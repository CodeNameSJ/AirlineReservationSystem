document.addEventListener('DOMContentLoaded', () => {
	const form = document.getElementById('flightSearchForm');
	if (!form) return;
	form.addEventListener('submit', (ev) => {
		ev.preventDefault();
		const params = new URLSearchParams(new FormData(form));
		const action = form.getAttribute('action') || '/flights';
		const url = new URL(action, window.location.origin);
		params.forEach((v, k) => {
			if (v) url.searchParams.append(k, v);
		});
		const fullUrl = url.toString();
		if (window.location.pathname.startsWith('/flights')) {
			url.searchParams.set('ajax', 'true');
			fetch(url, {method: 'GET', headers: {'Accept': 'text/html', 'X-Requested-With': 'XMLHttpRequest'}})
				 .then(resp => {
					 if (!resp.ok) throw new Error('Network response not ok: ' + resp.status);
					 return resp.text();
				 })
				 .then(html => {
					 const resultsEl = document.getElementById('searchResults');
					 if (resultsEl) {
						 resultsEl.innerHTML = html;
					 } else {
						 window.location.href = fullUrl;
					 }
				 })
				 .catch(err => {
					 console.debug('AJAX search failed, redirecting', err);
					 window.location.href = fullUrl;
				 });
		} else {
			// Not on /flights page: do full redirect
			window.location.href = fullUrl;
		}
	});
});