# Development

## Lint Github workflows

Run the following scrip to lint the Github workflows:

```shell
./actionlint.sh
```

## Check image vulnerabilities

Run the following script to check the image for vulnerabilities:

```shell
./trivy.sh
```
Note that it will download the complete vulnerability database on the first run, and that will take some time.
