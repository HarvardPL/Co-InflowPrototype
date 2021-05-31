## (Oakland 2021 Co-Inflow paper) Co-Inflow Prototype Java 8

### Paper

This repo contains a prototype implementation of our Oakland 2021 paper: Co-Inflow: Coarse-grained Information Flow Control for Java-like Languages.([link](https://people.seas.harvard.edu/~chong/abstracts/XiangC2021.html))

### Overview
The directory of this repo is the following: 
```
.
├── Co-Inflow.release       # Co-Inflow prototype release
├── Co-Inflow.demo          # The example shown in the paper
├── IFSPEC                  # Core suite of IFSpec benchmark
├── IFSpec_harness          # Harness program for using Co-Inflow tool on the IFSpec benchmarks

```

Except for the _IFSPEC_, the other three are implemented as Eclispe projects. Project _Co-Inflow.demo_ and _IFSpec_harness_ depend on the project _Co-Inflow.release_ to run. The projects can be exported to runnable jars. We may work on migrating the projects to maven later.   

The current prototype doesn't focus on performance. It should have a great potential for improvement. 

### Co-Inflow.release

The implementation relies on source code rewriting. The source code analysis tool [Spoon](https://spoon.gforge.inria.fr/) is used to insert calls to Co-Inflow runtime in the source code. The resulting Java programs explicitly track the current label of containers and the field and object labels of objects, and constructs and destructs labeled values and opaque labeled values. 

#### Structure
```
├── lbs.harvard.coinflow                     # User APIs, in particular, the CoInflowUserAPI.java
├── lbs.harvard.coinflow.compiler            # Co-Inflow compiler implementation. 
                                               The compiler is implemented as a series of Spoon processors.
                                               CoInflowCompiler.java is the main file.
├── lbs.harvard.coinflow.internal            # Co-Inflow runtime. IFCUtil.java is the main file. 
├── lbs.harvard.coinflow.lattice             # Lattice for Co-Inflow
├── lbs.harvard.coinflow.lattice.impl        # A graph implementation of the lattice
├── lbs.harvard.coinflow.lattice.principal   # Principal used for lattice, mostly reserved for future use
├── lbs.harvard.coinflow.util                # Helper classes. _Rewriter.java_ is the main file. 
├── lbs.harvard.coinflow.util.rifl           # RIFLParser.java parses rifl.xml (for IFSPEC benchmarking)
```


### Co-Inflow.demo
The phone number example shown in the paper.

#### Usage
Run the CoInflowCompiler program with two arguments: (1) a source folder, and (2) a destination folder. For example, from the command line: 

java CoInflowCompiler "pathto/coinflow.demos/src/" "pathto/output/folder"

### IFSPEC
The core suite of the IFSPEC benchmarks ([paper](https://pp.ipd.kit.edu/uploads/publikationen/ifspec18nordsec.pdf) and [download](www.spp-rs3.de/IFSpec)). It is a subset of the original benchmark suite: we delete the cases that Co-Inflow cannot correctly handle, e.g., reflections.   

### IFSpec_harness 
This provides a program, _IFSpecCompilation.java_, that automatically compiles the _IFSpec_ benchmark suites into Co-Inflow versions. 

#### Usage
In the _IFSpecCompilation_ program, specify the location of the _IFSPEC_ folder, and the destination folder of the compiled Co-Inflow programs. Then run IFSpecCompilation to process the whole folder. 


#### Compilation 
The original benchmarks will be compiled into different packages named with the benchmark' names, i.e., a new package will be produced for each benchmark suite. Originial benchmarks specify source and sink channels in their corresponding _rifl.xml_ files. The compilation process will read these files and insert label checks accordingly in the Co-Inflow versions.    

#### Running the Co-Inflow version
A _RunSample.java_ file will be created in every package. Running this program will (a) create a lattice specifed by the _rifl.xml_ file, and (2) run the Co-Inflow version with the lattice. 


