/**
 * filter for ViewC
 */
function candidateFilter() {
	// Declare variables
	var input, filter, table, tr, td, Name, fName, lName, i, txtValue;
	input = document.getElementById('filter');
	filter = input.value.toUpperCase();
	table = document.getElementById('myTable');
	tr = table.getElementsByTagName('tr');

	// Loop through all list items, and hide those who don't match the search
	// query
	for (i = 0; i < tr.length; i++) {
		fName = tr[i].getElementsByTagName('td')[1];
		lName = tr[i].getElementsByTagName('td')[2];
		if (fName || lName) {
			fNameValue = fName.textContent || fName.innerText;
			lNameValue = lName.textContent || lName.innerText;
			if (fNameValue.toUpperCase().indexOf(filter) > -1 || lNameValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = 'none';
			}
		}
	}
}