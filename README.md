# Sequence Method

## Getting started
It's a prover of logical formulas. Core of application is Sequential Method algorithm.
Let's look closer on application architecture. Project is divided in several independent
modules: Tokenizer, Infix-To-Prefix converter, AST generator, Sequential method and Wrapper.
<div align="center">
    <img src="https://github.com/lionell/sequential-method/blob/master/src/main/resources/static/images/logo.png" alt="Logo">
</div>

## Tree examples
Let's look closer at examples.

**Example 1.** First example, has only one branch. It shows how exactly
sequences is expanding when implication and disjunction are in charge.
```C
                                              P[x] = P[x] || Q[x]
                                                      |
                                                      v
                                            -{P[x] -> P[x] || Q[x]}
                                                      |
                                                      v
                                            +P[x], -{P[x] || Q[x]}
                                                      |
                                                      v
                                             -P[x], -Q[x], +P[x]
                                                      |
                                                      v
                                                      X
```
This is very simple example. Let's look on something harder.

**Example 2.** Here is more complicated example with quantifiers.
It's also truthful, but now we have two different closed branches.
```C
                                           #xP[x] -> Q[x] = P[x] -> #xQ[x]
                                                          |
                                                          v
                                        -{(#xP[x] -> Q[x]) -> P[x] -> #xQ[x]}
                                                          |
                                                          v
                                        +{#xP[x] -> Q[x]}, -{P[x] -> #xQ[x]}
                                       /                                   \
                                      /                                     \
                                     v                                       v
                        -#xP[x], -{P[x] -> #xQ[x]}                 +Q[x], -{P[x] -> #xQ[x]}
                                    |                                         |
                                    v                                         v
                    -P[x], -{P[x] -> #xQ[x]}, -#xP[x]                +P[x], -#xQ[x], +Q[x]
                                    |                                         |
                                    v                                         v
                     +P[x], -#xQ[x], -P[x], -#xP[x]               -Q[x], +P[x], +Q[x], -#xQ[x]
                                    |                                         |
                                    v                                         v
                                    X                                         X

```

## Formal Language Specification

                                    ### Backus-Naur Form
                                    ```HTML+PHP
                                    <expression>				::= <formula> "=" <formula>
                                    <formula>					::= [ "(" ] <predicate>
                                    										| <logical-operation>
                                    										| <quantifier>
                                    								[ ")" ]
                                    <predicate> 				::= <predicate-name> "[" <variable-arguments> "]"
                                    <predicate-name>			::= <capital-letter> { <letter> }
                                    <predicate-arguments> 		::= <variable-name> { "," <variable-name> }
                                    <variable-name> 			::= <letter> { <letter> }

                                    <logical-operation>			::= <unary-operation>
                                    								| <binary-operation>
                                    <binary-operation>			::= <formula> <binary-operation-keyword> <formula>
                                    <binary-operation-keyword>	::= "&&"
                                    								| "||"
                                    								| "->"
                                    <unary-operation>			::= "!" <formula>

                                    <quantifier>				::= <quantifier-keyword> <predicate-name> <formula>
                                    <quantifier-keyword>		::= "#"       comment stands for "exists"
                                    								| "@"     comment stands for "for all"

                                    <letter>					::= "a"
                                    								| "b"
                                    								| ...
                                    								| "z"
                                    <capital-letter>			::= "A"
                                    								| "B"
                                    								| ...
                                    								| "Z"
                                    ```

                                    ### Valid examples
                                     * `P[x] = Q[x]`
                                     * `P[x] = P[x] || Q[x]`
                                     * `#xP[x] -> Q[x] = P[x] -> #xQ[x]`
                                     * `#x@yP[x, y] = @y#xP[x, y]`
                                     * `P[x] -> #xQ[x] = #xP[x] -> Q[x]`

## API
There are only one service available, named `check`. To use it your query
should contains field named `expr` with expression you want to check.
Both `get` and `post` methods are supported.

### How to use API
Simple request `check?expr=P[x]=P[x]||Q[x]` will give you response
listed below.

```JSON
{
   "tree":{
      "root":{
         "formulas":[
            {
               "formula":"(P[x]) -> ((P[x]) || (Q[x]))",
               "value":false
            }
         ],
         "children":[
            {
               "formulas":[
                  {
                     "formula":"P[x]",
                     "value":true
                  },
                  {
                     "formula":"(P[x]) || (Q[x])",
                     "value":false
                  }
               ],
               "children":[
                  {
                     "formulas":[
                        {
                           "formula":"Q[x]",
                           "value":false
                        },
                        {
                           "formula":"P[x]",
                           "value":false
                        },
                        {
                           "formula":"P[x]",
                           "value":true
                        }
                     ],
                     "children":[],
                     "closed":true
                  }
               ],
               "closed":false
            }
         ],
         "closed":false
      }
   },
   "verity":true,
   "examples":null,
   "error":null
}
```

Now let's look closer to response structure.

### Response
```JSON
"tree":         sequential tree
"verity":       true,               if expression is truthful
                false,              if expression is false
                null,               if infinite loop detected
"examples":     null,               if expression is truthful
                counter example,    otherwise
"error":        error message,      if error occurred
                null,               otherwise
```

### node
```JSON
"formulas":     list of formulas
"children":     list of children
"closed":       true,               if sequence is closed
                false,              otherwise
```

### formula
```JSON
"formula":      actual formula
"value":        true,               if formula holds true
                false,              otherwise
```

### examples
```JSON
"name":         name of example
"delta":        map with new
                variables
"example":      map of predicates
                and their values
                on new variables
```

## Installation
It's a Maven project, so you need Maven to be installed to build app.

### Requirements
 * Java Runtime Environment with Java SE8 support
 * Maven 3 --- *TO BUILD*

### Build
You can build app using Maven with ease. Just type `mvn clean package`.
Then get the JAR at `target/sequential-method-1.0-SNAPSHOT.jar`.

### Running
If you are using Maven, you can run application without building using
`mvn spring-boot:run`. Or if you have pre-built JAR you can run it by typing:

`java -jar target/sequential-method-1.0-SNAPSHOT.jar`


### How to use
Now that the service is up, visit http://localhost:8080/, where you see
main page.

Write expression you want to check to marked textarea, and click
submit button.

Next page will show you result of sequential method applied to your expression.
Here you can find picture of sequential tree, verdict, counter example(if exists)
or error message.

For visualizing tree [d3.js](http://d3js.org) is used.
Design of main page from [HTML5 UP](http://html5up.net).

## Licence
```
The MIT License (MIT)

Copyright (c) 2015 Ruslan Sakevych

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

