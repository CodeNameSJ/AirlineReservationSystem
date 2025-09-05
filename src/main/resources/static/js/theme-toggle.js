(function () {
	const ID = 'theme-switch';
	const storageKey = 'theme';

	function applyTheme(theme) {
		if (!theme) return;
		document.documentElement.setAttribute('data-theme', theme);
		// update UI aria state on the label (role="switch")
		const label = document.querySelector(`label[for="${ID}"]`);
		if (label) label.setAttribute('aria-checked', String(theme === 'dark'));
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
		} catch (e) { /* ignore */
		}
	}

	function detectSystemPrefersDark() {
		try {
			return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
		} catch (e) {
			return false;
		}
	}

	function updateToggleUI(checkbox) {
		const label = document.querySelector(`label[for="${ID}"]`);
		if (label) label.setAttribute('aria-checked', String(checkbox.checked));
	}

	function init() {
		const checkbox = document.getElementById(ID);
		if (!checkbox) return;

		// initial theme selection
		const saved = readSavedTheme();
		const initial = saved || (detectSystemPrefersDark() ? 'dark' : 'light');
		applyTheme(initial);
		checkbox.checked = initial === 'dark';
		updateToggleUI(checkbox);

		// when user toggles checkbox
		checkbox.addEventListener('change', () => {
			const theme = checkbox.checked ? 'dark' : 'light';
			applyTheme(theme);
			writeSavedTheme(theme);
			updateToggleUI(checkbox);
			window.dispatchEvent(new CustomEvent('themechange', {detail: {theme}}));
		});

		// respect system changes only if user hasn't saved a preference
		try {
			const mq = window.matchMedia('(prefers-color-scheme: dark)');
			if (mq.addEventListener) {
				mq.addEventListener('change', (e) => {
					if (readSavedTheme()) return;
					const theme = e.matches ? 'dark' : 'light';
					applyTheme(theme);
					checkbox.checked = e.matches;
					updateToggleUI(checkbox);
				});
			} else if (mq.addListener) {
				mq.addListener((e) => {
					if (readSavedTheme()) return;
					const theme = e.matches ? 'dark' : 'light';
					applyTheme(theme);
					checkbox.checked = e.matches;
					updateToggleUI(checkbox);
				});
			}
		} catch (e) { /* ignore */
		}

		// keyboard support: allow Enter/Space toggling on the visible label
		const label = document.querySelector(`label[for="${ID}"]`);
		if (label) {
			label.addEventListener('keydown', (ev) => {
				if (ev.code === 'Space' || ev.code === 'Enter' || ev.key === ' ') {
					ev.preventDefault();
					checkbox.checked = !checkbox.checked;
					checkbox.dispatchEvent(new Event('change', {bubbles: true}));
				}
			});
		}

		// expose helper
		window.toggleTheme = function () {
			checkbox.checked = !checkbox.checked;
			checkbox.dispatchEvent(new Event('change', {bubbles: true}));
		};
	}

	if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init); else init();
})();