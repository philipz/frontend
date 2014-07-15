(ns frontend.components.docs.missing-file)

(def article
  {:title "An important file is missing from your repository"
   :last-updated "Feb 3, 2013"
   :url :missing-file
   :content [:div
             [:p
              "Developers commonly omit certain files from their repositories, especially files containing application settings, passwords and API keys.However, these files can be essential for the proper running of their programs, and often contain settings which can't be reasonable inferred by CircleCI."]
             [:p
              "If you wish to remove important files from your repository, you should add a rough copy of the file under a similar name, such asYou can then copy it to its correct place with some simple commands:"]
             [:pre
              "’‘"
              [:code.bash
               "’checkout:  post:    - cp config/app.yml.ci config/app.yml‘"]
              "’‘"]
             [:p
              "For a"
              [:code "database.yml"]
              "file, it's even simpler - just include it without the important settings like production and staging database keys."]]})

