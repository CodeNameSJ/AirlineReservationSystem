document.addEventListener('DOMContentLoaded', () => {
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
		const dd = btn.closest('.dropdown');
		if (!dd) return;
		const menu = dd.querySelector('.dropdown-content');
		btn.addEventListener('click', (e) => {
			e.preventDefault();
			e.stopPropagation();
			const opening = dd.classList.toggle('show');
			btn.setAttribute('aria-expanded', opening ? 'true' : 'false');
			if (opening && menu) {
				const first = menu.querySelector('a, button, [tabindex]:not([tabindex="-1"])');
				if (first) first.focus();
			}
			closeAll(opening ? dd : null);
		});
		btn.addEventListener('keydown', (e) => {
			if (e.key === 'Enter' || e.key === ' ') {
				e.preventDefault();
				btn.click();
			} else if (e.key === 'Escape') {
				dd.classList.remove('show');
				btn.setAttribute('aria-expanded', 'false');
				btn.focus();
			}
		});
		if (menu) {
			menu.addEventListener('keydown', (e) => {
				if (e.key === 'Escape') {
					dd.classList.remove('show');
					btn.setAttribute('aria-expanded', 'false');
					btn.focus();
				}
			});
		}
	});
	document.addEventListener('click', (e) => {
		if (!e.target.closest('.dropdown')) closeAll();
	});
	document.addEventListener('keydown', (e) => {
		if (e.key === 'Escape') closeAll();
	});
});