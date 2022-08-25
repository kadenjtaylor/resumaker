# Resu-Maker

Tool for building up a resume using Scala, then invoking LaTeX to compile it into a presentable PDF.

## Running the application

```shell
sbt run
```

Then check the configured output directory (ex: `latex/`) for your PDF resume!

## Working on the Resume

Pull up the compiled pdf in a tab, and run

```shell
sbt ~run
```
and watch the pdf update on save.

# TODOs
 - Organize technical skills section better
  - Differentiate between programming languages, libraries, tools