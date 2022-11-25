(ns dx-docs.manifest)

(def documents
  {:clinvar {:label "ClinVar"
             :source "/markdown/clinvar.md"}
   :index {:label "Index"
           :source "/markdown/index.md"}
   :policy {:label "Policy"
            :source "/markdown/policy.md"}})

(def schemas
  {:core {:label "Common Concepts"
          :schema-uri "/schemas/core.json"}
   :vrs {:label "Variation"
         :schema-uri "/schemas/vrs.json"
         :docroot "https://vrs.ga4gh.org/en/stable/terms_and_model.html#"}
   :va {:label "Variant Annotation"
        :schema-uri "/schemas/annotation.json"}
   :vod {:label "Variation Object Descriptor"
         :schema-uri "/schemas/vod.json"
         :docroot "https://vrsatile.readthedocs.io/en/latest/value_object_descriptor/index.html#"}
   :catvars {:label "Categorical Variation"
             :schema-uri "/schemas/catvars.json"
             :docroot "https://vrsatile.readthedocs.io/en/latest/catvars/index.html#"}})
