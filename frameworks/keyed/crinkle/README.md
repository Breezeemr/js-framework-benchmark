# crinkle ~ Benchmark

## Overview

This is a app built using [Crinkle](https://github.com/favila/crinkle).
That will meet the specification outlined by and for this [benchmark](https://github.com/krausest/js-framework-benchmark).


## install

``` shell
`npm install`

`npm run build-dev`


# development
``` shell

`npm run build-dev`
```

now follow the instructions in the terminal.


## Clean

``` shell
npm run clean
```

## Release

``` shell
`npm build-prod`
```


## Benchmarking just this project

See instructions in benchmarking repo for how to [benchmark a single project.](https://github.com/krausest/js-framework-benchmark#4-running-a-single-framework-with-the-automated-benchmark-driver)

but it will probably look like

1. install webdriver

```shell
cd webdriver-ts
npm install
npm run build-prod
```

2. run benchmark against crinkle

warning this will take about 5 minutes and open a browser repeatedly.

```
npm run bench keyed/crinkle
```

3. check results in webdriver-ts/results directory (see link above for more)



## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
