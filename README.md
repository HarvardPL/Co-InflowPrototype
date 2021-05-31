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
├── Co-Inflow.release       # original version of the IFSpec benchmark suite
├── Co-Inflow.demo          # an example of using the Co-Inflow tool
├── IFSPEC                  # Core suite of IFSpec benchmark
├── IFSpec_harness          # harness program for using Co-Inflow tool on the IFSpec benchmarks

```

### Co-Inflow.release

The implementation relies on source code rewriting. The source code analysis tool [Spoon](https://spoon.gforge.inria.fr/) is used to insert calls to Co-Inflow runtime in the source code. The resulting Java programs explicitly track the current label
of containers and the field and object labels of objects, and constructs and destructs labeled values and opaque labeled
values. The prototype is released as a eclipse project. 

### Co-Inflow.demo
The phone number example shown in the paper.

### IFSPEC
The core suite of the IFSPEC benchmarks ([paper](https://pp.ipd.kit.edu/uploads/publikationen/ifspec18nordsec.pdf) and [download](www.spp-rs3.de/IFSpec)). It is a subset of the original benchmark suite: we delete the cases that Co-Inflow cannot correctly handle, e.g., reflections.   

### IFSpec_harness 
This provides a program, IFSpecCompilation.java, that automatically compiles the IFSpec benchmark suites into Co-Inflow versions. 

#### Usage
In the IFSpecCompilation program, specify the location of the IFSPEC folder, and the destination folder of the compiled Co-Inflow programs. Then run IFSpecCompilation to process the whole folder. 

#### Processing
The original benchmarks will be compiled into different packages named with the benchmark' names. So a new package will be produced for each benchmark suite. Originial benchmarks specify source and sink channels in their corresponding rifl.xml file. These   
