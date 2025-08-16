document.addEventListener('DOMContentLoaded', function () {
	// Use the existing markup: .drop-btn toggles nearest .dropdown
	const dropButtons = Array.from(document.querySelectorAll('.drop-btn'));

	function closeAll(except = null) {
		document.querySelectorAll('.dropdown').forEach(dd => {
			if (dd !== except) {
				dd.classList.remove('show');
				const btn = dd.querySelector('.drop-btn');
				if (btn) btn.setAttribute('aria-expanded', 'false');
			}
		});
	}

	dropButtons.forEach(btn => {
		// ensure aria-controls points to actual menu; if not, skip
		const dd = btn.closest('.dropdown');
		if (!dd) return;
		const menu = dd.querySelector('.dropdown-content');

		// Click toggles menu
		btn.addEventListener('click', function (e) {
			e.preventDefault();
			e.stopPropagation();
			const opening = dd.classList.toggle('show');
			btn.setAttribute('aria-expanded', opening ? 'true' : 'false');
			// focus first menu item when opened (keyboard-friendly)
			if (opening && menu) {
				const first = menu.querySelector('a, button, [tabindex]:not([tabindex="-1"])');
				if (first) first.focus();
			}
			closeAll(opening ? dd : null);
		});

		// Keyboard activation on Enter / Space
		btn.addEventListener('keydown', function (e) {
			if (e.key === 'Enter' || e.key === ' ') {
				e.preventDefault();
				btn.click();
			} else if (e.key === 'Escape') {
				dd.classList.remove('show');
				btn.setAttribute('aria-expanded', 'false');
				btn.focus();
			}
		});

		// close a menu when pressing Escape while focused inside a menu
		if (menu) {
			menu.addEventListener('keydown', (e) => {
				if (e.key === 'Escape') {
					dd.classList.remove('show');
					if (btn) btn.setAttribute('aria-expanded', 'false');
					btn.focus();
				}
			});
		}
	});

	// click outside closes menus
	document.addEventListener('click', function (e) {
		if (!e.target.closest('.dropdown')) {
			closeAll(null);
		}
	});

	// global Escape closes all
	document.addEventListener('keydown', function (e) {
		if (e.key === 'Escape') closeAll(null);
	});
});