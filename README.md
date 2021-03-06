# Data analysis project

A supervised deep learning algorithm that learns to recognize handwritten digits from pictures using a decision tree model.
The learning algorithm takes as input a training set(an examples of pictures that for each one is attached the true label of this picture), and use it to build a predictor (a function that maps each example to his label by the algorithm) by building a desicion tree. Finally examine the success rates by using the test set (an examples of pictures the algorithm has never seen before) on the predictor.

## Prerequisites:
Train and test sets in csv format. You can download it from this link: https://pjreddie.com/projects/mnist-in-csv

## How to Run:

Clone this repository : https://github.com/eliormatan/mini-project.git

Then use this command lines on an unix based operating system:

```
$ ./learntree <1/2> <P> <L> <trainingset_filename> <outputtree_filename> 
$ ./predict <tree_filename> <testset_filename>
```

When:\
1/2 - version of the learning algorithm to run\
P - the percent of the training set that should be used for validation\
L - the maximal power of 2 for tree sizes T\

## How the algorithm build the decision tree:
//todo

### How the algorithm choose the tree size:
//todo

### What are the conditions in each version:
//todo

#### version 1:
//todo

#### version 2: 
//todo

