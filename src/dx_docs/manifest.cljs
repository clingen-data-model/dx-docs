(ns dx-docs.manifest)

(def documents
  {:clinvar {:label "ClinVar Getting Started"
             :source "/markdown/clinvar.md"}
   :index {:label "Index"
           :source "/markdown/index.md"}
   :policy {:label "ClinVar Policies"
            :source "/markdown/policy.md"}})

(def schemas
  {:core {:label "Common Concepts"
          :schema-uri "/schemas/core.json"
          :metaschema-uri "/schemas/core-source.json"}
   :vrs {:label "Variation"
         :schema-uri "/schemas/vrs.json"
         :metaschema-uri "/schemas/vrs-source.json"
         :docroot "https://vrs.ga4gh.org/en/stable/terms_and_model.html#"}
   :va {:label "Variant Annotation"
        :schema-uri "/schemas/annotation.json"
        :metaschema-uri "/schemas/annotation-source.json"}
   :vod {:label "Variation Object Descriptor"
         :schema-uri "/schemas/vod.json"
         :metaschema-uri "/schemas/vod-source.json"
         :docroot "https://vrsatile.readthedocs.io/en/latest/value_object_descriptor/index.html#"}
   :catvars {:label "Categorical Variation"
             :schema-uri "/schemas/catvars.json"
             :metaschema-uri "/schemas/catvars-source.json"
             :docroot "https://vrsatile.readthedocs.io/en/latest/catvars/index.html#"}})
