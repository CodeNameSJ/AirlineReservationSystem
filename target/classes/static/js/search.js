document.addEventListener('DOMContentLoaded', () => {
	const form = document.getElementById('flightSearchForm');
	if (!form) return;

	// 🔥 ADD THIS BLOCK HERE
	let timeout;
	form.addEventListener('input', () => {
		clearTimeout(timeout);
		timeout = setTimeout(() => {
			form.dispatchEvent(new Event('submit'));
		}, 300);
	});

	form.addEventListener('submit', (ev) => {
		ev.preventDefault();

		const formData = new FormData(form);
		const params = new URLSearchParams();

		formData.forEach((value, key) => {
			if (typeof value === 'string') {
				params.append(key, value);
			}
		});
		const action = form.getAttribute('action') || '/flights';
		const url = new URL(action, window.location.origin);

		params.forEach((v, k) => {
			if (v) url.searchParams.append(k, v);
		});

		const fullUrl = url.toString();

		if (window.location.pathname.startsWith('/flights')) {

			fetch(url, {
				method: 'GET',
				headers: {
					'Accept': 'text/html',
					'X-Requested-With': 'XMLHttpRequest'
				}
			})
				 .then(resp => {
					 if (!resp.ok) throw new Error('Network error: ' + resp.status);
					 return resp.text();
				 })
				 .then(html => {
					 const resultsEl = document.getElementById('searchResults');
					 if (!resultsEl) {
						 window.location.href = fullUrl;
						 return;
					 }

					 // prevent injecting full page
					 if (html.includes('<html')) {
						 window.location.href = fullUrl;
						 return;
					 }

					 resultsEl.innerHTML = html;
				 })
				 .catch(() => {
					 window.location.href = fullUrl;
				 });

		} else {
			window.location.href = fullUrl;
		}
	});
});