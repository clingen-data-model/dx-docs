# ClinVar GK-Pilot Applied Schema Elements

spec      : https://gk-pilot.readthedocs.io/en/latest/

schemas   : https://github.com/ga4gh/va-spec/tree/metakb-cvc/schema/*-source.yaml 

dataset   : https://github.com/ga4gh/gk-pilot/tree/main/datasets/clinvar/*

## ns:annotation Statements

### VariationGermlinePathogenicityStatement (1.0a1)

```
  source   :  https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits :  VariationConditionStatement
                VariationStatement
                  Statement
                    InformationEntity
                      gks.core:ExtensibleEntity
                        gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    * id                : gks.core:Entity>InformationEntity : 1..1 : string 
    type                : gks.core:Entity.type              : 1..1 : string : const "VariationGermlinePathogenicityStatement" 
    * label             : gks.core:ExtensibleEntity         : 0..1 : string 
    description         : InformationEntity                 : 0..1 : string
    target_proposition  : InformationEntity                 : 0..1 : VariationGermlinePathogenicityProposition
    subject_descriptor  : VariationStatement                : 0..1 : oneOf [ VariationDescriptor | CategoricalVariationDescriptor | gks.core:CURIE ] 
    * object_descriptor : VariationConditionStatement       : 0..1 : oneOf [ DiseaseDescriptor | ConditionDescriptor | PhenotypeDescriptor | gks.core:CURIE ] 
    classification      : Statement.conclusion              : 0..1 : gks.core:Coding  
    direction           : Statement                         : 0..1 : enum: [ "supports", "uncertain", "opposes" ]
    contributions       : InformationEntity                 : 0..* : Contribution
    record_metadata     : InformationEntity                 : 0..1 : gks.core:RecordMetaData 
    extensions          : gks.core:ExtensibleEntity         : 0..* : Extension
    specified_by        : InformationEntity                 : 0..1 : oneOf [ Method | gks.core:CURIE ]
    * variation_origin  : VariationStatement                : 0..1 : enum: [ "germline", "somatic" ]
    * is_reported_in    : InformationEntity                 : 0..* : oneOf [ Document | gks.core:CURIE ]
    * evidence_level    : InformationEntity                 : 0..1 : gks.core:Coding
  ]
  
* not used by ClinVar data pipeline 
```   

### VariationGermlinePathogenicityProposition (1.0a1)
used by: VariationGermlinePathogenicityStatement.target_proposition

```
  source     :  https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits   :  VariationProposition
                  Proposition
                    gks.core:ValueEntity
                      gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    * id              : gks.core:Entity>gks.core:ValueEntity  : 0..1 : gks.core:CURIE 
    type              : gks.core:Entity.type                  : 1..1 : string : const "VariationGermlinePathogenicityProposition" 
    subject           : gks.core:ValueEntity                  : 1..1 : oneOf [ vrs:Variation | catvars:CategoricalVariation | gks.core:CURIE ]
    predicate         : string                                : 1..1 : enum  [ "causes_mendelian_condition", "increases_risk_for_condition","decreases_risk_for_condition" ]
    object            : gks.core:ValueEntity                  : 1..1 : oneOf [ gks.core:Disease | gks.core:Condition | gks.core:Phenotype ]
  ]

* not used by ClinVar data pipeline    
```

### Contribution (1.0a1)
used by: VariationGermlinePathogenicityStatement.contributions

```
  source     :  https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits   :  gks.core:ExtensibleEntity
                  gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    * id              : gks.core:Entity           : 0..1 : gks.core:CURIE 
    type              : gks.core:Entity.type      : 1..1 : string : const  "Contribution" 
    contributor       : Contribution              : 0..1 : Agent
    date              : Contribution              : 0..1 : string (format:date)
    activity          : Contribution              : 0..1 : gks.core:Coding
  ]

* not used by ClinVar data pipeline 
```

### Agent (1.0a1)
used by: Contribution.contributor

```
  source     :  https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits   :  gks.core:ExtensibleEntity
                  gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    * id              : gks.core:Entity           : 0..1 : gks.core:CURIE 
    type              : gks.core:Entity.type      : 1..1 : string : const  "Agent" 
    name              : Agent                     : 0..1 : string
    * subtype         : Agent                     : 0..1 : gks.core:Coding
  ]

* not used by ClinVar data pipeline 
```    

### Method (1.0a1)
used by: VariationGermlinePathogenicityStatement.specified_by

```
  source     :  https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits   :  gks.core:ExtensibleEntity
                  gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    * id              : gks.core:Entity           : 0..1 : gks.core:CURIE 
    type              : gks.core:Entity.type      : 1..1 : string : const  "Method" 
    is_reported_in    : Method                    : 0..1 : oneOf [ Document | gks.core:CURIE ]
    * subtype           : Method                    : 0..1 : gks.core:Coding
  ]

* not used by ClinVar data pipeline 
```
    
### Document (1.0a1)
used by: Method.is_reported_in

```
  source     : https://github.com/ga4gh/va-spec/blob/metakb-cvc/schema/annotation-source.yaml
  inherits   :  gks.core:ExtensibleEntity
                  gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    id                : gks.core:Entity           : 0..1 : gks.core:CURIE 
    type              : gks.core:Entity.type      : 1..1 : string : const  "Document" 
    * xrefs           : Document                  : 0..* : gks.core:CURIE
    * title           : Document                  : 0..1 : string
  ]

* not used by ClinVar data pipeline 
```
    
## ns:vod ValueObjectDescriptors

### CanonicalVariationDescriptor (1.0a1)
used by: VariationGermlinePathogenicityStatement.subject_descriptor

```
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  CategoricalVariationDescriptor
                  ValueObjectDescriptor
                    gks.core:ExtensibleEntity
                      gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  properties : [
    id                              : gks.core:Entity>ValueObjectDescriptor                 : 1..1 : string 
    type                            : gks.core:Entity.type                                  : 1..1 : string - const"CanonicalVariationDescriptor" 
    label                           : gks.core:ExtensibleEntity                             : 0..1 : string 
    description                     : ValueObjectDescriptor                                 : 0..1 : string
    canonical_variation             : CategoricalVariationDescriptor.categorical_variation  : 1..1 : oneOf [ catvars:CanonicalVariation |gks.core:CURIE ]
    * subject_variation_descriptor  : CanonicalVariationDescriptor                          : 0..1 : VariationDescriptor
    members                         : CategoricalVariationDescriptor                        : 0..* : VariationMember
    xrefs                           : ValueObjectDescriptor                                 : 0..* : gks.core:CURIE
    * alternate_labels              : ValueObjectDescriptor                                 : 0..* : string
    extensions                      :  gks.core:ExtensibleEntity                            : 0..* : Extension
  ]

* not used by ClinVar data pipeline 
```
  
### VariationMember (1.0a1)
used by: CanonicalVariationDescriptor.members

```
  (NOTE: @ahwagner this should inherit from Entity or have "type" as required)
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  <none>
  maturity   : alpha
  version    : 1.0a1
  used by    : CanonicalVariationDescriptor.members
  properties : [
    type                : VariationMember      : 0..1 : string - const "VariationMember" 
    expressions         : VariationMember      : 1..* : Expression
    variation           : VariationMember      : 0..1 : oneOf [ vrs:Variation | gks.core:CURIE ]
  ]

* not used by ClinVar data pipeline 
```

  
### VariationDescriptor (1.0a1)
used by: VariationGermlinePathogenicityStatement.subject_descriptor

```
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  ValueObjectDescriptor
                  gks.core:ExtensibleEntity
                    gks.core:Entity
  maturity   : alpha
  version    : 1.0a1
  used by    : CanonicalVariationDescriptor.subject_variation_descriptor
  properties : [
    id                  : gks.core:Entity>ValueObjectDescriptor : 1..1 : string 
    type                : gks.core:Entity.type                  : 1..1 : string - const "VariationDescription" 
    variation           : ValueObjectDescriptor.value           : 1..1 : oneOf [ vrs:Variation | gks.core:CURIE ]
    label               : gks.core:ExtensibleEntity             : 0..1 : string 
    description         : ValueObjectDescriptor                 : 0..1 : string
    xrefs               : ValueObjectDescriptor                 : 0..* : gks.core:CURIE
    expressions         : VariationDescriptor                   : 0..* : Expression
    gene_context        : VariationDescriptor                   : 0..* : oneOf [ GeneDescriptor | gks.core:CURIE ]
    alternate_labels    : ValueObjectDescriptor                 : 0..* : string
    extensions          : gks.core:ExtensibleEntity             : 0..* : Extension
    *molecule_context   : VariationDescriptor                   : 0..1 : enum  [ "genomic", "transcript", "protein" ]
    *structural_type    : VariationDescriptor                   : 0..1 : gks.core:CURIE
    *vrs_ref_allele_seq : VariationDescriptor                   : 0..1 : vrs:Sequence
    *allelic_state      : VariationDescriptor                   : 0..1 : gks.core:CURIE
  ]

* not used by ClinVar data pipeline 
```
  
### ConditionDescriptor
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  ValueObjectDescriptor
                  gks.core:ExtensibleEntity
                    gks.core:Entity
  maturity   : draft
  version    : 1.0d1
  used by : 
  properties : [
    condition : oneOf [ gks.core:Condition | gks.core:CURIE ]
    member_descriptors : anyOf [ DiseaseDescriptor | PhenotypeDescriptor ]

### GeneDescriptor
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  ValueObjectDescriptor
                  gks.core:ExtensibleEntity
                    gks.core:Entity
  maturity   : draft
  version    : 1.0d1
  used by    : 
  properties : [
    type  : ??    : 1..1 : string const  "GeneDescriptor"
    gene  : ??    oneOf [ gks.core:Gene | gks.core:CURIE ]

### PhenotypeDescriptor
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  ValueObjectDescriptor
                  gks.core:ExtensibleEntity
                    gks.core:Entity
  maturity   : draft
  version    : 1.0d1
  used by : 
  properties : [
    type  : ??    : 1..1 : string const  "PhenotypeDescriptor"
    gene  : ??    oneOf [ gks.core:Phenotype | gks.core:CURIE ]

### DiseaseDescriptor
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  ValueObjectDescriptor
                  gks.core:ExtensibleEntity
                    gks.core:Entity
  used by    : 
  properties : [
    type  : ??    : 1..1 : string const  "DiseaseDescriptor"
    gene  : ??    : oneOf [ gks.core:Disease | gks.core:CURIE ]

### Expression
  source     :  https://github.com/ga4gh/vrsatile/blob/97f3606b0ef5293b06d6899e18ed56cb3dead43b/schema/vod-source.yaml
  inherits   :  <none>
  maturity   : draft
  version    : 1.0d1
  used by    : VariationDescriptor.expressions, VariationMember.expressions
  properties : [
    type            : Expression  : 1..1 : string const "Expression"
    syntax          : Expression  : 1..1 : enum ["hgvs.c", "hgvs.p", "hgvs.g", "hgvs.m", "hgvs.n", "hgvs.r", "iscn", "gnomad", "spdi"]
    value           : Expression  : 1..1 : string
    syntax_version  : Expression  : 0..1 : string
  ]
  
## ns: catvars

### CanonicalVariation



Extension
used by : VariationDescriptor.extensions, CanonicalVariationDescriptor.extensions, VariationGermlinePathogenicityStatement.extensions

## ns: vrs

### Variation
used by : VariationDescriptor.variation, VariationMembeer.variation

### Sequence
used by : VariationDescriptor.*vrs_ref_allele_seq
