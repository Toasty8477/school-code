#!/bin/bash
# echo "Expected Results"
# echo ""
# cat $1
# echo ""
# echo "Actual Results"
# echo""
# cat $2
# echo ""
# echo "Differences"
# echo ""
# diff -w $1 $2
# echo "Wide Format Differences"
# echo ""
diff -q -y -w $1 $2
