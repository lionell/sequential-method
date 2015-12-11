# Sequence Method

## Formal Language Specification

### Backus-Naur Form
```
<expression>				::= <formula> "->" <formula>
<formula>					::= [ "(" ] <predicate>
										| <logical-operation>
										| <quantifier>
								[ ")" ]
<predicate> 				::= <predicate-name> "(" <variable-arguments> ")"
<predicate-name>			::= <capital-letter> { <letter> }
<predicate-arguments> 		::= <variable-name> { "," <variable-name> }
<variable-name> 			::= <letter> { <letter> }

<logical-operation>			::= <unary-operation>
								| <binary-operation>
<binary-operation>			::= <formula> <binary-operation-keyword> <formula>
<binary-operation-keyword>	::= "&"
								| "||"
								| "->"
<unary-operation>			::= "!" <formula>

<quantifier>				::= <quantifier-keyword> <predicate-name> "[" <formula> "]"
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