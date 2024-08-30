# Contributing

**Note:** [This work](README.md) is licensed under the [Apache License 2.0](LICENSE.txt).
If you contribute any [non-trivial](http://www.gnu.org/prep/maintain/maintain.html#Legally-Significant) patches make sure to read and agree with the license.

#### Would you like to contribute code?

Keep your pull requests small, otherwise it is less likely they will get merged.

1. [Fork trakt-java](https://github.com/UweTrottmann/trakt-java/fork).
2. Create a new branch and make [great commits](http://robots.thoughtbot.com/post/48933156625/5-useful-tips-for-a-better-commit-message).
   - If you add or modify endpoints, make sure to also create and run basic tests (see `src/test`).
   - If you make non-trivial changes (see above), make sure to add a copyright line at the top of the file. 
3. [Start a pull request](https://github.com/UweTrottmann/trakt-java/compare) against `main`.

#### No code!
* You can [discuss or submit bug reports](https://github.com/UweTrottmann/trakt-java/issues)!

## Setup

This project is built with [Maven](https://maven.apache.org/), see the `pom.xml` in the root folder.

The tests provide temporary credentials (see [BaseTestCase](src/test/java/com/uwetrottmann/trakt5/BaseTestCase.java)),
so running tests should just work.
If an unauthorized error is returned, let me know that the test credentials need to be refreshed.
