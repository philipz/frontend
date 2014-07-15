(ns frontend.components.docs.composer-api-rate-limit)

(def article
  {:title "Composer hitting GitHub API rate limits"
   :last-updated "May 23, 2013"
   :url :composer-api-rate-limit
   :content [:div
             [:p
              [:i
               "Note that this only applies when setting up your composer step manually - CircleCI's built-in PHP support knows how to workaround this issue."]]
             [:p
              "Composer uses the GitHub API to download dependencies.Unfortunately, GitHub has strict API rate limits, which can cause your builds to fail when fetching dependencies.This causes an error that looks like this:"]
             [:pre
              "’‘"
              [:code.no-highlight
               "’Could not fetch https://api.github.com/repos/username/repo/v1.2.3, enter your GitHub credentials to go over the API rate limitThe credentials will be swapped for an OAuth token stored in /home/ubuntu/.composer/config.json, your password will not be storedTo revoke access to this token you can visit https://github.com/settings/applicationsUsername:‘"]
              "’‘"]
             [:p
              "To get around this, tell composer to use git to fetch dependencies instead:"]
             [:pre
              "’‘"
              [:code.bash "’composer install --prefer-source --no-interaction‘"]
              "’‘"]]})

