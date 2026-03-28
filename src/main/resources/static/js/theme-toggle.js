(function () {
	const ID = 'theme-switch';
	const storageKey = 'theme';

	function applyTheme(theme) {
		if (!theme) return;
		document.documentElement.setAttribute('data-theme', theme);
	}

	function init() {
		const checkbox = document.getElementById(ID);
		if (!checkbox) return;

		const label = document.querySelector(`label[for="${ID}"]`);

		let saved;
		try {
			saved = localStorage.getItem(storageKey);
		} catch {
			saved = null;
		}

		let hasUserPreference = !!saved;

		const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
		const initial = saved || (prefersDark ? 'dark' : 'light');

		applyTheme(initial);
		checkbox.checked = initial === 'dark';

		if (label) {
			label.setAttribute('aria-checked', String(checkbox.checked));
		}

		checkbox.addEventListener('change', () => {
			const theme = checkbox.checked ? 'dark' : 'light';
			applyTheme(theme);

			try {
				localStorage.setItem(storageKey, theme);
				hasUserPreference = true;
			} catch {
			}

			if (label) {
				label.setAttribute('aria-checked', String(checkbox.checked));
			}

			window.dispatchEvent(new CustomEvent('themechange', {detail: {theme}}));
		});

		const mq = window.matchMedia('(prefers-color-scheme: dark)');
		mq.addEventListener?.('change', (e) => {
			if (hasUserPreference) return;

			const theme = e.matches ? 'dark' : 'light';
			applyTheme(theme);
			checkbox.checked = e.matches;

			if (label) {
				label.setAttribute('aria-checked', String(checkbox.checked));
			}
		});
	}

	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', init);
	} else {
		init();
	}
})();