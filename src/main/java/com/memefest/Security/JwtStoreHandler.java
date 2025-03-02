package com.memefest.Security;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
/*
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.INVALID;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.NOT_VALIDATED;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID; 
*/
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Alternative
@Priority(2000)
public class JwtStoreHandler implements IdentityStoreHandler {
  private List<IdentityStore> validatingIdentityStrore;
  
  private List<IdentityStore> groupProvidingIdentityStores;
  
  @PostConstruct
  public void init() {
    List<IdentityStore> identityStores = getBeanReferencesByType();
    this
      
      .validatingIdentityStrore = (List<IdentityStore>)identityStores.stream().filter(i -> i.validationTypes().contains(IdentityStore.ValidationType.VALIDATE)).sorted(Comparator.comparing(IdentityStore::priority)).collect(Collectors.toList());
    this
      
      .groupProvidingIdentityStores = (List<IdentityStore>)identityStores.stream().filter(i -> i.validationTypes().contains(IdentityStore.ValidationType.PROVIDE_GROUPS)).sorted(Comparator.comparing(IdentityStore::priority)).collect(Collectors.toList());
  }
  
  public List<IdentityStore> getBeanReferencesByType() {
    return (List<IdentityStore>)CDI.current().select(IdentityStore.class, new java.lang.annotation.Annotation[0]).stream().collect(Collectors.toList());
  }
  
  public CredentialValidationResult validate(Credential credential) {
    CredentialValidationResult result = null;
    IdentityStore identityStore = null;
    for (IdentityStore authorizingStore : this.validatingIdentityStrore) {
      CredentialValidationResult temp = authorizingStore.validate(credential);
      switch (temp.getStatus()) {
        case INVALID:
        case NOT_VALIDATED:
          result = temp;
          break;
        case VALID:
          result = temp;
          identityStore = authorizingStore;
          break;
        default:
          throw new IllegalArgumentException("Value not supported" + temp.getStatus());
      } 
      if (result != null && result.getStatus() == CredentialValidationResult.Status.INVALID)
        break; 
    } 
    if (result == null)
      return CredentialValidationResult.INVALID_RESULT; 
    if (result.getStatus() != CredentialValidationResult.Status.VALID)
      return result; 
    CallerPrincipal callerPrincipal = result.getCallerPrincipal();
    Set<String> groups = new HashSet<>();
    if (identityStore.validationTypes().contains(IdentityStore.ValidationType.PROVIDE_GROUPS))
      groups.addAll(result.getCallerGroups()); 
    for (IdentityStore authorizationIdentityStore : this.groupProvidingIdentityStores)
      groups.addAll(authorizationIdentityStore.getCallerGroups(result)); 
    return new CredentialValidationResult(callerPrincipal, groups);
  }
}
