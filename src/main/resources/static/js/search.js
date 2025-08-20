// search.js (drop-in)
// Behavior:
// - If user is NOT on /flights -> do a full redirect to /flights?...
// - If user IS on /flights -> try AJAX: fetch fragment with ajax=true and replace #searchResults
// - If AJAX fails -> fallback to full redirect

document.addEventListener('DOMContentLoaded', () => {
	const form = document.getElementById('flightSearchForm');
	if (!form) return;

	form.addEventListener('submit', (ev) => {
		// Build a URLSearchParams from the form fields
		const fd = new FormData(form);
		const params = new URLSearchParams();
		for (const [k, v] of fd.entries()) {
			if (v == null || v === '') continue;
			params.append(k, v);
		}

		// base action (use action attribute if present)
		const action = form.getAttribute('action') || '/flights';
		const baseUrl = new URL(action, window.location.origin);
		// Build full URL (no ajax param yet)
		for (const [k, v] of params.entries()) baseUrl.searchParams.append(k, v);
		const fullUrl = baseUrl.toString();

		// If we're on the flights page, prefer AJAX partial update
		if (window.location.pathname.startsWith('/flights')) {
			// ensure ajax=true is present
			baseUrl.searchParams.set('ajax', 'true');
			const ajaxUrl = baseUrl.toString();

			fetch(ajaxUrl, {
				method: 'GET',
				headers: { 'Accept': 'text/html', 'X-Requested-With': 'XMLHttpRequest' }
			})
				 .then(resp => {
					 if (!resp.ok) throw new Error('Network response not ok: ' + resp.status);
					 return resp.text();
				 })
				 .then(html => {
					 // replace the main results container if present
					 const resultsEl = document.getElementById('searchResults');
					 if (resultsEl) {
						 resultsEl.innerHTML = html;
					 } else {
						 // fallback to full redirect
						 window.location.href = fullUrl;
					 }
				 })
				 .catch(err => {
					 console.debug('AJAX search failed - falling back to full redirect', err);
					 window.location.href = fullUrl;
				 });

			ev.preventDefault();
			return;
		}

		// Not on /flights -> do a full redirect (server will render flights page)
		ev.preventDefault();
		// Navigate to /flights with query params (no ajax param)
		window.location.href = fullUrl;
	});
});