for ((i = 1; i <100; i++))
do
curl -H 'Content-type:text/xml' -d '<item><services><service 
name="UPC-analysis" date="2013-10-29"></service></services><sentences><sentence 
id="1"><text>Bruce Springsteen is an American singer-songwriter and multi-instrumentalist.</text><tokens><token pos="NP00SP0" end="17" lemma="bruce_springsteen" id="1.1" start="0">Bruce_Springsteen</token><token pos="VBZ" end="20" lemma="be" 
id="1.2" start="18">is</token><token pos="Z" end="23" lemma="1" id="1.3" start="21">an</token><token pos="NP00V00" end="32" lemma="american"  id="1.4" start="24">American</token><token pos="NN" end="50" lemma="singer-songwriter" id="1.5" 
start="33">singer-songwriter</token><token pos="CC" end="54" lemma="and" id="1.6" start="51">and</token><token pos="NN" end="76" lemma="multi-instrumentalist" id="1.7" 
start="55">multi-instrumentalist</token><token pos="Fp" end="77" lemma="." id="1.8" 
start="76">.</token></tokens></sentence></sentences><nodes><node type="entity" class="other" displayName="american" id="E2"><mentions><mention sentenceId="1" id="E2.1" words="American"><mention_token id="1.4"> </mention_token> </mention> </mentions></node><node type="entity" class="person" displayName="bruce_springsteen" 
id="E1"><mentions><mention sentenceId="1" id="E1.1" words="Bruce Springsteen"><mention_token id="1.1"> </mention_token> </mention></mentions> </node></nodes></item>' 'http://km.aifb.kit.edu/services/annotation-en/'
echo $i
done
