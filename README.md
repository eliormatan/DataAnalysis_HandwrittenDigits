# Data Analysis Handwritten Digits

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
Started with one leaf with the most common digit in the training set. In each round (number of rounds is determined by the input parameter T), one of the leaves in the tree is deleted, and replaced by a condition junction with two children who are leaves (The value in each new leaf will be the most common digit in the training sample for images that fit that leaf).

### How the algorithm choose which leaf to replace:
By using a measure called information gain (IG) improvement. This measure measures the entropy change in the entropy of the tree.
The algorithm selects a condition(from a set C) and a leaf which maximizes the IG * number of leaves from the chosen one. 

### How the algorithm choose the tree size:
By using validation set. The algorithm selects randomly P% from the samples to be the validation set, and the rest will be used for the training.
For each of the possible values of T, run the tree construction algorithm on the training sample for T. then the algorithm calculates the estimated algorithm success by applying the prediction model to the samples in the validation sample, and calculates the percentage of samples for which the algorithm prediction model matches the true label of the example. 
At the end of the pass on all the values of T, select the value of T for which the success on the validation sample was the highest. For this value, the tree learning algorithm is re-run on the entire sample, including the training part and the validation part. The generated tree is the output of the algorithm. The final success of the learning algorithm will be tested on the test sample.
//todo

### What are the conditions in each version:
#### version 1:
//todo

#### version 2: 
//todo

