document.documentElement.classList.add('no-transition');
(() => {
	const storageKey = 'theme';
	const checkbox = document.getElementById('theme-switch');
	const label = document.querySelector('.toggle');

	if (!checkbox) return;

	const saved = localStorage.getItem(storageKey);
	const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

	const initial = saved ?? (prefersDark ? 'dark' : 'light');

	document.documentElement.setAttribute('data-theme', initial);
	checkbox.checked = initial === 'dark';

	if (label) {
		label.setAttribute('aria-checked', String(checkbox.checked));
	}

	checkbox.addEventListener('change', () => {
		const theme = checkbox.checked ? 'dark' : 'light';

		document.documentElement.setAttribute('data-theme', theme);
		localStorage.setItem(storageKey, theme);

		label?.setAttribute('aria-checked', String(checkbox.checked));
	});

	// OPTIONAL: react to system change ONLY if no user preference
	if (!saved) {
		const mq = window.matchMedia('(prefers-color-scheme: dark)');
		mq.addEventListener('change', (e) => {
			const theme = e.matches ? 'dark' : 'light';
			document.documentElement.setAttribute('data-theme', theme);
			checkbox.checked = e.matches;
			label?.setAttribute('aria-checked', String(e.matches));
		});
	}
})();
window.addEventListener('load', () => {
	requestAnimationFrame(() => {
		document.documentElement.classList.remove('no-transition');
	});
});