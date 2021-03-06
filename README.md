# Sequential method

<div align="center">
    <img src="/src/main/resources/static/images/logo.png" alt="Logo">
</div>

A prover of logical formulas. Core of application is Sequential Method algorithm.

## How it works
Application is divided in several independent modules:
  1. [Parser][1-1] parse input string into AST.
     1. [Tokenizer][1-1-1] split input expression into lexemes.
     2. [Infix-to-Postfix converter][1-1-2] converts infix list of lexemes
        to RPN(Reverse Polish Notation).
     3. [Abstract Syntax Tree generator][1-1-3] converts list of
        tokens(token = lexeme + additional information) to tree.
  2. [Sequential method][1-2] logic of application.
  3. [Wrapper][1-3] wrap results of Sequential method to unique data structure for
        mapping to JSON.

[1-1]: /src/main/java/io/github/lionell/utils/analysis/Parser.java
[1-1-1]: /src/main/java/io/github/lionell/utils/analysis/Tokenizer.java
[1-1-2]: /src/main/java/io/github/lionell/utils/analysis/InfixToPostfixConverter.java
[1-1-3]: /src/main/java/io/github/lionell/utils/analysis/FormulaGenerator.java
[1-2]: /src/main/java/io/github/lionell/utils/SequentialMethod.java
[1-3]: /src/main/java/io/github/lionell/utils/WrapBuilder.java

Now let's move to the **heart** of application.

### Algorithm
Here are description of one algorithm iteration.
  1. If all leaves are closed, **finish with positive verdict**.
  2. If all leaves are atomic, **finish with negative verdict**.
  3. For each non-atomic, non-closed leaf
        * Expand leaf.
        * Simplify result leaves.
  4. Goto step 1

There is situation when algorithm will go to **INFINITE LOOP**.
In this case we can use [König's infinity lemma][kenigs] to
**finish with negative result**.

[kenigs]: https://en.wikipedia.org/wiki/K%C3%B6nig%27s_lemma

#### Sequential formulas

<table>
    <tr>
        <th>mark</th>
        <th>notation</th>
        <th colspan="2">branches</th>
    </tr>
    <tr>
        <td><sub>+</sub>!</td>
        <td><sub>+</sub>!A, &Sigma;</td>
        <td colspan="2"><sub>-</sub>A, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>-</sub>!</td>
        <td><sub>-</sub>!A, &Sigma;</td>
        <td colspan="2"><sub>+</sub>A, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>+</sub>v</td>
        <td><sub>+</sub>(A v B), &Sigma;</td>
        <td><sub>+</sub>A, &Sigma;</td>
        <td><sub>+</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>-</sub>v</td>
        <td><sub>-</sub>(A v B), &Sigma;</td>
        <td colspan="2"><sub>-</sub>A, <sub>-</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>+</sub>&</td>
        <td><sub>+</sub>(A & B), &Sigma;</td>
        <td colspan="2"><sub>+</sub>A, <sub>+</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>-</sub>&</td>
        <td><sub>-</sub>(A & B), &Sigma;</td>
        <td><sub>-</sub>A, &Sigma;</td>
        <td><sub>-</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>+</sub>-></td>
        <td><sub>+</sub>(A -> B), &Sigma;</td>
        <td><sub>-</sub>A, &Sigma;</td>
        <td><sub>+</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>-</sub>-></td>
        <td><sub>-</sub>(A -> B), &Sigma;</td>
        <td colspan="2"><sub>+</sub>A, <sub>-</sub>B, &Sigma;</td>
    </tr>
    <tr>
        <td><sub>+</sub>&exist;</td>
        <td><sub>+</sub>&exist;xA, &Sigma;</td>
        <td colspan="2"><sub>+</sub>A<sub>x</sub>[y], &Sigma;</td>
    </tr>
    <tr>
        <td><sub>-</sub>&exist;</td>
        <td><sub>-</sub>&exist;xA, &Sigma;</td>
        <td colspan="2">
            <sub>-</sub>A<sub>x</sub>[z<sub>1</sub>],
            ...,
            <sub>-</sub>A<sub>x</sub>[z<sub>m</sub>],
            &Sigma;,
            <sub>-</sub>&exist;xA
        </td>
    </tr>
    <tr>
        <td><sub>+</sub>&forall;</td>
        <td><sub>+</sub>&forall;xA, &Sigma;</td>
        <td colspan="2">
            <sub>+</sub>A<sub>x</sub>[z<sub>1</sub>],
            ...,
            <sub>+</sub>A<sub>x</sub>[z<sub>m</sub>],
            &Sigma;,
            <sub>+</sub>&forall;xA
        </td>
    </tr>
    <tr>
        <td><sub>-</sub>&forall;</td>
        <td><sub>-</sub>&forall;xA, &Sigma;</td>
        <td colspan="2"><sub>-</sub>A<sub>x</sub>[y], &Sigma;</td>
    </tr>
</table>

Where
  * y denotes **new unique** name.
  * z<sub>1</sub>, ..., z<sub>m</sub> denotes names **used in current sequence**.
  * A<sub>x</sub>[y] denotes **renomination** of predicate from x to y.

### Let's look at examples
#### Example 1
First example demonstrates how exactly sequences is expanding when implication
and disjunction are in charge.
```
  P[x] = P[x] | Q[x]
          |
          v
-(P[x] -> P[x] | Q[x])
          |
          v
+P[x], -(P[x] | Q[x])
          |
          v
 -P[x], -Q[x], +P[x]
          |
          v
          X
```
As you can see sequential tree has only one branch, and it's closed.
According to algorithm above, expression is **truthful**.
It's quite simple example, so let's move to something more interesting.

#### Example 2
Here is more complicated example with quantifiers.
It's also truthful, but now we have two different closed branches.
```
                       #xP[x] -> Q[x] = P[x] -> #xQ[x]
                                      |
                                      v
                    -((#xP[x] -> Q[x]) -> P[x] -> #xQ[x])
                                      |
                                      v
                    +(#xP[x] -> Q[x]), -(P[x] -> #xQ[x])
                   /                                   \
                  /                                     \
                 v                                       v
    -#xP[x], -(P[x] -> #xQ[x])                 +Q[x], -(P[x] -> #xQ[x])
                |                                         |
                v                                         v
-P[x], -(P[x] -> #xQ[x]), -#xP[x]                +P[x], -#xQ[x], +Q[x]
                |                                         |
                v                                         v
 +P[x], -#xQ[x], -P[x], -#xP[x]               -Q[x], +P[x], +Q[x], -#xQ[x]
                |                                         |
                v                                         v
                X                                         X
```
As you can see all two branches closed at the same time. So expression is
truthful and no counter examples exists.

#### Example 3
This example show you how to deal with unclosed sequences, and how to get a
counter example.
```
                 P[x] -> #xQ[x] = #xP[x] -> Q[x]
                                |
                                v
               -(P[x] -> #xQ[x] -> #xP[x] -> Q[x])
                                |
                                v
               +(P[x] -> #xQ[x]), -(#xP[x] -> Q[x])
              /                                   \
             /                                     \
            v                                       v
-P[x], -(#xP[x] -> Q[x])                 +#xQ[x], -(#xP[x] -> Q[x])
           |                                         |
           v                                         v
 +#xP[x], -Q[x], -P[x]                    +Q[z], -(#xP[x] -> Q[x])
           |                                         |
           v                                         v
  +P[y], -Q[x], -P[x]                      +#xP[x], -Q[x], +Q[z]
                                                     |
                                                     v
                                            +P[w], -Q[x], +Q[z]
```

##### Left branch

| name |  delta  |      values          |
|:----:|:--------|:---------------------|
|      | x -> a, | P\[a\] := **False,** |
|  A   | y -> b  | P\[b\] := **True,**  |
|      |         | Q\[a\] := **False**  |

Let's try to build interpretation on this counter example. There are many
different options, but we can stop on:
```
x := integer
P[x] := x == 1
Q[x] := x != x                // always false
```

##### Right branch

| name |  delta  |      values         |
|:----:|:--------|:--------------------|
|      | x -> a, | Q\[a] := **False,** |
|  B   | z -> b, | Q\[b] := **True,**  |
|      | w -> c  | P\[c] := **True**   |

In this case we can use next interpretation:
```
x := integer
P[x] := (x + x) % 2 == 0      // always true
Q[x] := x % 2 == 1
```

##### Summary
We have got two unclosed branches. Each one produces unique counter example.
Each counter example can give us many different interpretations. We have
already built some of them. You can manually check these interpretations,
to ensure that expression is false. Just evaluate input expression with these
interpretations.

Let's stop at this example and move forward to syntax overview.

## Language specification
This is language grammar in [Backus-Naur-Form][bnf].

[bnf]: https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_Form

```HTML+PHP
<expression>                ::= <formula> "=" <formula>
<formula>                   ::= [ "(" ]
                                <predicate>
                              | <logical-operation>
                              | <quantifier>
                                [ ")" ]
<predicate>                 ::= <predicate-name> "[" <variable-arguments> "]"
<predicate-name>            ::= <capital-letter> { <letter> }
<predicate-arguments>       ::= <variable-name> { "," <variable-name> }
<variable-name>             ::= <letter> { <letter> }

<logical-operation>         ::= <unary-operation>
                              | <binary-operation>
<binary-operation>          ::= <formula> <binary-operation-keyword> <formula>
<binary-operation-keyword>  ::= "&"
                              | "|"
                              | "->"
<unary-operation>           ::= "!" <formula>

<quantifier>                ::= <quantifier-keyword> <predicate-name> <formula>
<quantifier-keyword>        ::= "#"        Stands for "exists"
                              | "@"        Stands for "for all"

<letter>                    ::= "a"
                              | "b"
                              | ...
                              | "z"
<capital-letter>            ::= "A"
                              | "B"
                              | ...
                              | "Z"
```

If your expression **does not fit** this grammar then application will rise an
parser exception and inform you with `error` field of response.

### Valid expressions
Some valid examples of expressions:
  * `P[x] = Q[x]`
  * `P[x] = P[x] | Q[x]`
  * `#xP[x] -> Q[x] = P[x] -> #xQ[x]`
  * `#x@yP[x, y] = @y#xP[x, y]`
  * `P[x] -> #xQ[x] = #xP[x] -> Q[x]`
  * `P[x] | Q[x] | R[x] | T[x] | S[x] = @xP[x]`
  * `@xP[x] = Q[x]`

## API
There are only one service available, named `check`. To use it your query
should contains field named `expr` with expression you want to check.
Both `get` and `post` methods are supported.

### API Usage
Simple request `check?expr=P[x]=P[x]|Q[x]` will give you response
listed below.

```JSON
{
   "tree":{
      "root":{
         "formulas":[
            {
               "formula":"P[x] -> Q[x]",
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
                     "formula":"Q[x]",
                     "value":false
                  }
               ],
               "children":[],
               "closed":false
            }
         ],
         "closed":false
      }
   },
   "verity":false,
   "examples":[
      {
         "name":"E",
         "delta":{
            "x":"m"
         },
         "example":{
            "P":{
               "[m]":true
            },
            "Q":{
               "[m]":false
            }
         }
      }
   ],
   "error":null
}
```

Now let's look closer to response structure.

### Response structure
<table>
    <tr>
        <td>&nbsp;</td>
        <th>name</th>
        <th>type</th>
        <th>description</th>
    </tr>
    <tr>
        <th rowspan="4">response</th>
        <td>tree</td>
        <td>{root: node}</td>
        <td>sequential tree with root node</td>
    </tr>
    <tr>
        <td>verity</td>
        <td>Boolean</td>
        <td>null, if infinite loop</td>
    </tr>
    <tr>
        <td>examples</td>
        <td>[example]</td>
        <td>list of examples</td>
    </tr>
    <tr>
        <td>error</td>
        <td>string</td>
        <td>error message from server</td>
    </tr>
    <tr>
        <th rowspan="3">node</th>
        <td>formulas</td>
        <td>[formula]</td>
        <td>list of formulas</td>
    </tr>
    <tr>
        <td>children</td>
        <td>[node]</td>
        <td>list of child nodes</td>
    </tr>
    <tr>
        <td>closed</td>
        <td>boolean</td>
        <td>is sequence closed</td>
    </tr>
    <tr>
        <th rowspan="2">formula</th>
        <td>formula</td>
        <td>string</td>
        <td>actual formula</td>
    </tr>
    <tr>
        <td>value</td>
        <td>boolean</td>
        <td>logical value</td>
    </tr>
    <tr>
        <th rowspan="3">example</th>
        <td>name</td>
        <td>string</td>
        <td>name of example</td>
    </tr>
    <tr>
        <td>delta</td>
        <td>{x -> a}</td>
        <td>map of new variable names</td>
    </tr>
    <tr>
        <td>example</td>
        <td>{P[x] -> val}</td>
        <td>map of predicates/variables/values</td>
    </tr>
</table>

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

### Docker
You can run this application using prebuild docker image like this `docker run -it -p 8080:8080 lionell/math-logic`.
Alternatively you can use Docker Compose `docker-compose up -d`. This will start service locally and expose port 8080.

## Used materials
Here is a list of materials used in app:
  * [D3.js](http://d3js.org) visualizing sequential tree
  * [HTML5 UP](http://html5up.net) design of main page
  * [Spring](https://spring.io) framework
  * [Maven](https://maven.apache.org) app architecture
  * [Docker](https://www.docker.com) isolated environment

## Contributions
Here I want to say thanks to my friend [Fetiorin](http://vk.com/id234442497)
who helped me with this app.

He did a great job on the client-side.

## Licence
MIT
