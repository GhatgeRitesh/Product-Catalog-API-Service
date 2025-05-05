#!/bin/sh
#echo "Script Execution Started"
#
#echo "Sending GET Request"
#
#values=$(curl -s -X GET http://localhost:8080/product/getAll)
#
#
#if [ -z "$values"  ]; then
#  echo " The Request Is Successfull"
#  echo " The result received is : "
#  echo "$values"
#
#else
#   echo " The request Failed "
#fi
#
#echo "The script ends here "

echo "Script Execution Started"

echo "Sending GET Request"

values=$(curl -s -X GET http://localhost:8080/product/getAll)

if [ -n "$values" ]; then  # Checks if response is NOT empty
  echo "The Request Is Successful"
  echo "The result received is:"
  echo "$values"
else
  echo "The request Failed"
fi

echo "The script ends here"
