(function () {
	const ID = 'theme-switch';
	const storageKey = 'theme';

	function applyTheme(theme) {
		if (!theme) return;
		document.documentElement.dataset.theme = theme;
	}

	function readSavedTheme() {
		try {
			return localStorage.getItem(storageKey);
		} catch (e) {
			return null;
		}
	}

	function writeSavedTheme(theme) {
		try {
			localStorage.setItem(storageKey, theme);
		} catch (e) {
		}
	}

	function detectSystemPrefersDark() {
		try {
			return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
		} catch (e) {
			return false;
		}
	}

	function init() {
		const checkbox = document.getElementById(ID);
		if (!checkbox) return;
		const saved = readSavedTheme();
		const initial = saved || (detectSystemPrefersDark() ? 'dark' : 'light');
		applyTheme(initial);
		checkbox.checked = initial === 'dark';

		checkbox.addEventListener('change', () => {
			const theme = checkbox.checked ? 'dark' : 'light';
			applyTheme(theme);
			writeSavedTheme(theme);
			window.dispatchEvent(new CustomEvent('themechange', {detail: {theme}}));
		});

		try {
			const mq = window.matchMedia('(prefers-color-scheme: dark)');
			mq.addEventListener && mq.addEventListener('change', (e) => {
				if (readSavedTheme()) return; // don't override user's choice
				applyTheme(e.matches ? 'dark' : 'light');
				checkbox.checked = e.matches;
			});
		} catch (e) { /* ignore */
		}
	}

	window.toggleTheme = function () {
		const checkbox = document.getElementById(ID);
		if (!checkbox) return;
		checkbox.checked = !checkbox.checked;
		checkbox.dispatchEvent(new Event('change', {bubbles: true}));
	};

	if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init); else init();
})();