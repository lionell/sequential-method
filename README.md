# Sequence Method

## Formal Language Specification

### Backus-Naur Form
```
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
<quantifier-keyword>		::= "#" comment stands for "exists"
								| "@" comment stands for "for all"

<letter>					::= "a" 
								| "b" 
								| ... 
								| "z"
<capital-letter>			::= "A" 
								| "B" 
								| ... 
								| "Z"
```

### Examples of expressions
 * `P[x] = Q[x]`
 * `P[x] = P[x] || Q[x]`
 * `#x(P[x] -> Q[x]) = P[x] -> #xQ[x]`
 * `@x(P[x] || Q[x]) = @xP[x] || @xQ[x]`
 * `@x(P[x] && Q[x] -> R[y]) = #xP[x]`
 * `#xP[x] -> Q[x] = P[x] -> #xQ[x]`
 * `#x@yP[x, y] = @y#xP[x, y]`

## Examples
Let's look closer at examples.

**Example 1.** First example, has only one branch. It shows how exactly sequences is expanding
when implication and disjunction are in charge.

```
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
Generated `json` for this example will looks like
```
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
This is very simple example. Let's look on something harder.

**Example 2.** Here is more complicated example with quantifiers. It's also truthful,
but now we have tow different closed branches.
```
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

## API
There are only one service available, named `check`. To use it your query
should contains field named `expr` with expression you want to check.

For example `localhost:8080/check?expr=#yP[x]->@xQ[y]=Q[x]` will give response in `json`.

Now let's look closer to response of the service.

### Response
```
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
```
"formulas":     list of formulas
"children":     list of children
"closed":       true,               if sequence is closed
                false,              otherwise
```

### formula
```
"formula":      actual formula
"value":        true,               if formula holds true
                false,              otherwise
```

## Installation

### Requirements
 * JRE 1.8(at least)

### Running
To run the application you should follow the steps above

 * Start Tomcat server with application.
 * Go to `localhost:8080/index.html`
 * That's all (:

This will run a RESTful server with `check` service. Web-client uses this
service to get a tree representation and verdict in `json` format. Then all
data is processed with `d3.js` and a pretty nice graphic is generated.

