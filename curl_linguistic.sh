for ((i = 1; i <100; i++))
do
curl -H 'Content-type:text/xml' -d '<analyze><text>Michael Jordan pets  a dog.</text></analyze>' 'http://sandbox-xlike.isoco.com/services/analysis_en/analyze'
echo $i
done
