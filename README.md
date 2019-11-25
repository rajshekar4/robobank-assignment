<h1>Rabobank Customer Statement Processor</h1>
Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated.

<div class="paragraph">
<p>The format of the file is a simplified version of the MT940 format. The format is as follows:</p>
</div>
<table class="tableblock frame-all grid-all spread">
<caption class="title">Table 1. Record description</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Field</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Transaction reference</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">A numeric value</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Account number</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">An IBAN</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Start Balance</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The starting balance in Euros</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Mutation</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Either an addition (+) or a deduction (-)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">Description</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Free text</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">End Balance</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">The end balance in Euros</p></td>
</tr>
</tbody>
</table>

<div class="sect1">
<h2 id="_output">Output</h2>
<div class="sectionbody">
<div class="paragraph">
<p>There are two validations:</p>
</div>
<div class="ulist">
<ul>
<li>
<p>all transaction references should be unique</p>
</li>
<li>
<p>the end balance needs to be validated</p>
</li>
</ul>
</div>
<div class="paragraph">
<p>At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records.</p>
</div>
</div>
</div>
</div>
<div id="footer">
</div>

<h3>Steps to run the application:</h3>

1. Clone the project Rabobank (Spring boot project).
  git command : git clone https://github.com/rajshekar4/robobank-assignment
  
2. Run batch (run.bat) from your terminal/command prompt 
3. it will do mvn clean install and runs all junit unit test cases and started the application.
4. Open any REST api testing tool (Postman client) : http://localhost:8080/api/v1/process
5. Upload input csv/xml file in the service using postman client and select post 
6. The input file will be validated based on two conduction mentioned in the problem statment.(validation condition mentioned in expected output section)
Duplicate Transaction key check,
End balance calculation check. (endbalance = startbalance – mutation)
7.Finally invalid/failure records will be sent as response of rest api.
      
<H3>code coverage report : </h3>

<img src="https://github.com/rajshekar4/robobank-assignment/blob/master/documentation/codecoverage.JPG"/>

<H3>Screen shots</h3>

<h2> End balance calculation check.</h2> <br>
<img src="https://github.com/rajshekar4/robobank-assignment/blob/master/documentation/end_balance.JPG"/> <br>

<h2> all transaction references should be unique. displaying failure records </h2> <br>

<img src="https://github.com/rajshekar4/robobank-assignment/blob/master/documentation/duplicate_records.JPG"/> <br>

