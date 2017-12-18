## Releasing

### Preparation

You will need credentials for publishing to the "typesafe"
organization on Bintray.

### Release steps

1. Update the version number in `build.sbt` and `README.md`. Commit the changes.
2. Tag the commit with e.g. `git tag -a 9.8.7` and push the tag
   to GitHub.
3. Create the release at https://github.com/lightbend/lightbend-emoji/releases .
4. Locally, run `+publish` (the `+` is for cross-publishing for multiple
   Scala version) followed by `bintrayRelease`.

If this last step succeeds, you will see e.g.  `typesafe/emoji@9.8.7
was released`.  The artifacts will become available within a minute or two.
