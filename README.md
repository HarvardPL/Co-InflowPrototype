## (Oakland 2021 Co-Inflow paper) Co-Inflow Prototype Java 8

### Paper

This repo contains a prototype implementation of our Oakland 2021 paper: Co-Inflow: Coarse-grained Information Flow Control for Java-like Languages.([link](https://people.seas.harvard.edu/~chong/abstracts/XiangC2021.html))

### Contents
This repo includes the following

1. Co-Inflow prototype
2. IFSpec benchmarking results
3. Case studies appeared in the paper

The directory of this repo is the following: 
```
.
├── IFSpec_original         # original version of the IFSpec benchmark suite
├── IFSpec_Coinflow         # IFSpec benchmark test cases with Co-Inflow annotations
├── IFSpec_harness          # harness program for using Co-Inflow prototype on the IFSpec
├── Prototype               # Co-Inflow prototype
├── Case_studies            # three case studies
│   ├── HR.                 # a Human Resources application
│   ├── HealthPlus          # a Health Records application
│   ├── Roller              # the Roller application
```

### Prototype

The implementation relies on source code rewriting. The source code analysis tool [Spoon](https://spoon.gforge.inria.fr/) is used to insert calls to Co-Inflow runtime in the source code. The resulting Java programs explicitly track the current label
of containers and the field and object labels of objects, and constructs and destructs labeled values and opaque labeled
values. The prototype is released as a eclipse project. 

