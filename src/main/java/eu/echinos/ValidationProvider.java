package eu.echinos;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.validation.FhirValidator;
import java.io.IOException;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.NpmPackageValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;

public class ValidationProvider extends FhirValidator {

  public ValidationProvider(FhirContext ctx) throws IOException {
    super(ctx);

    // Create a chain that will hold our modules
    ValidationSupportChain supportChain = new ValidationSupportChain();

    // DefaultProfileValidationSupport supplies base FHIR definitions. This is generally required
    // even if you are using custom profiles, since those profiles will derive from the base
    // definitions.
    DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);
    supportChain.addValidationSupport(defaultSupport);

    supportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(ctx));
    supportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(ctx));
    //snapshot generation
    supportChain.addValidationSupport(new SnapshotGeneratingValidationSupport(ctx));

    NpmPackageValidationSupport npmPackageSupport = new NpmPackageValidationSupport(ctx);
    npmPackageSupport.loadPackageFromClasspath("classpath:package/de.gematik.elektronische-versicherungsbescheinigung-1.0.0-rc3.tgz");


  }
}
