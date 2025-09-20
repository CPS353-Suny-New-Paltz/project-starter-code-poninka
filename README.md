# Power Digit Sum Computation

API Diagram:

+---------+        Network API        +---------------------------+
|  User   | ------------------------> |  Compute Engine           |
+---------+                           |  Controller               |
                                      +-------------+-------------+
                                         |          |
+---------------+                        |          | Conceptual
| Data Storage  | <----------------------+          | API
| System        |       Process API                 |
+---------------+                                   v
                                      +---------------------------+
                                      |  Computation Core         |
                                      | (PowerDigitSumController) |
                                      +---------------------------+


The api will use the sum of the digits of a number raised to a power.
For example, for an input of 2^15 = 32768, the output would be 3 + 2 + 7 + 6 + 8 = 26.
