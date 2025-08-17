document.addEventListener('DOMContentLoaded', function () {
	// Attach to any search forms present
	document.querySelectorAll('#flightSearchForm').forEach(function (form) {
		form.addEventListener('submit', function (e) {
			e.preventDefault();
			const fd = new FormData(form);
			const params = new URLSearchParams();
			for (const [k, v] of fd.entries()) {
				if (v instanceof File) continue;
				params.append(k, v);
			}
			params.set('ajax', 'true');

			const action = form.getAttribute('action') || '/flights';
			const url = action + '?' + params.toString();

			fetch(url, {
				method: 'GET',
				headers: {
					'X-Requested-With': 'XMLHttpRequest',
					'Accept': 'text/html'
				}
			})
				 .then(response => {
					 if (!response.ok) throw new Error('Network response was not ok');
					 return response.text();
				 })
				 .then(html => {
					 // Prefer a nearby results container; fall back to #searchResults
					 let target = form.closest('.flight-search-card')?.querySelector('#searchResults') || document.getElementById('searchResults');
					 if (!target) {
						 // if no target, try to append a new container inside the form's parent
						 const div = document.createElement('div');
						 div.id = 'searchResults';
						 form.parentNode.insertBefore(div, form.nextSibling);
						 target = div;
					 }
					 target.innerHTML = html;
				 })
				 .catch(err => console.error('Search request failed', err));
		});
	});
});