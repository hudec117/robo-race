# Contributing

## Required software

- [JDK 11](https://adoptopenjdk.net/)
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/)
- [git](https://git-scm.com/downloads)

## Optional but recommend software

- [GitHub Desktop](https://desktop.github.com/)
  - Will make working with git easier for beginners.

## Cloning the repository

Clone this repository using the CLI or GitHub desktop.

## Setting up Eclipse

When opening Eclipse you will most likely be prompted to select a workspace, navigate to your cloned repository and select the `src` folder as your workspace.

If you are not prompted, go to `File -> Switch Workspace -> Other...`

If all has gone well, you should see `robo.race` and `robo.race.tests` projects in the Package Explorer.

## Branching Strategy

- `master` will represent a release-able state of the software.
- `dev` will be used to merge feature branches.
- `feature/<feature>` will be used for feature branches.

Merging between the above branches will be done exclusively using Pull Requests on GitHub.