# Sequential method

## Getting started
It's a prover of logical formulas. Core of application is Sequential Method algorithm.
Let's look closer on application architecture. Project is divided in several independent
modules: Tokenizer, Infix-To-Prefix converter, AST generator, Sequential method and Wrapper.
<div align="center">
    <img src="https://github.com/lionell/sequential-method/blob/master/src/main/resources/static/images/logo.png" alt="Logo">
</div>

## How it works
Application is divided in several independent modules:
 1. Parser -- parse input string into AST. See Parser class.
    1. Tokenizer -- split input expression into lexemes. See Tokenizer class.
    2. Infix-to-Postfix converter -- converts list of lexemes
        into RPN(Reverse Polish Notation). See InfixToPostfixConverter class.
    3. Abstract Syntax Tree generator -- converts list of
        tokens(token = lexeme + additional information) to tree.
        See FormulaGenerator class.
 2. Sequential method -- logic of application. See SequentialMethod class.
 3. Wrapper -- wrap results of Sequential method to unique data structure for
        mapping to JSON. See WrapBuilder class.

Now let's move to the hurt for application.

### Algorithm
Here are description of one algorithm iteration.
 1. If all leaves are closed, **finish with positive verdict**.
 2. If all leaves are atomic, **finish with negative verdict**.
 4. For each non-atomic & non-closed leaf => *leaf*
    1. Expand *leaf* leaf.
    2. Simplify result leaves.
 5. Goto step 1

There is situation when algorithm will be in **INFINITE LOOP**.
In this case we can use Kenig's lemma to say
about **finish with negative result**.

Now let's look closer at examples.

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
As you can see all two branches closed at the same time. So expression is
truthful and there are no counter example exists.

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

### response
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
### Requirements
 * Java Runtime Environment with Java SE8 support
 * Maven 3 to build

### Building
You can build app using Maven with ease. Just type `mvn clean package`.
This will generate JAR file `target/sequential-method-1.0-SNAPSHOT.jar`.

### Running
If you are using Maven, you can run application without building using
`mvn spring-boot:run`.

Or if you have pre-built JAR you can run it by typing:

`java -jar target/sequential-method-1.0-SNAPSHOT.jar`


### How to use
Now that the service is up, visit [http://localhost:8080/](http://localhost:8080/),
where you see main page.

Write expression you want to check to marked textarea, and click
submit button.

Next page will show you result of sequential method applied to your expression.
Here you can find picture of sequential tree, verdict, counter example(if exists)
or error message.

## Used technologies
Here is a list of materials used in app:
 * [d3.js](http://d3js.org) visualizing sequential tree
 * [HTML5 UP](http://html5up.net) design of main page
 * [Spring](https://spring.io/) framework
 * [Maven](https://maven.apache.org/) app architecture

## Contributions
Here I want to say thanks to my friend [Fetiorin](http://vk.com/id234442497)
who helped me with this app.

You did a great job on client-side. Thank you very much.

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

