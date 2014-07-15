(ns frontend.components.docs.status-badges)

(def article
  {:title "Embeddable status badges"
   :last-updated "June 28, 2013"
   :url :status-badges
   :content [:div
             [:p
              "We provide project and branch status images, which you can embed anywhere you want: a GitHubREADME, a dashboard, or anywhere else you can imagine!"]
             [:p
              "Here's our current build status:"
              [:img
               {:src
                "https://circleci.com/gh/circleci/circle.png?circle-token=3cc80b12ab3627373c76e13735b8bc00a1259b9e"}]]
             [:p
              "You can use a simple image URL like this to see the status of your project's default branch:"
              [:code
               "https://circleci.com/gh/:owner/:repo.png?circle-token=:circle-token"]]
             [:p
              "For example:"
              [:a
               {:href
                "https://circleci.com/gh/circleci/mongofinil.png?circle-token=b14acf911433d315298235b0c2fbf7b2670a92a8"}
               "https://circleci.com/gh/circleci/mongofinil.png?circle-token=b14ac..."]]
             [:p
              "Or you can see the status of any specific branch:"
              [:code
               "https://circleci.com/gh/:owner/:repo/tree/:branch.png?circle-token=:circle-token"]]
             [:p
              "For example:"
              [:a
               {:href
                "https://circleci.com/gh/circleci/mongofinil/tree/master.png?circle-token=b14acf911433d315298235b0c2fbf7b2670a92a8"}
               "https://circleci.com/gh/circleci/mongofinil/tree/master.png?circle-token=b14ac..."]]
             [:p
              "One small wrinkle: if your branch name includes characters that are \\special\\ in URLs (most commonly '/') they'll need to be"
              [:a
               {:href "http://www.w3schools.com/tags/ref_urlencode.asp"}
               "URL encoded"]
              "when constructing status badge URLs. For example, the badge for a branch called 'feature/nonexistent' would be at '.../tree/feature%2Fnonexistent.png'."]
             [:h3 "Access Control"]
             [:p
              "We recommend creating specific, narrowly scoped API tokens. In fact, we created a wholeclass of tokens just for this purpose: tokens which give read-only access to the buildstatus of a single project, and nothing else. You can create and manage these tokens fromthe "
              [:b "Edit settings > API Tokens"]
              " page of any project."]
             [:p
              "If you prefer, it is also possible to use a user's API token as the circle-token. Thesetokens are very powerful, so only do that in a trusted environment!"]
             [:p
              "(You can also link to the image without providing a circle-token, in which case the imagewill only appear for people who are currently logged-in to circleci.com!)"]]})
