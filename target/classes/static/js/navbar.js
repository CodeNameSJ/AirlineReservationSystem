document.addEventListener('click', (e) => {
	const btn = e.target.closest('.drop-btn');

	if (!btn) {
		// click outside → close all
		document.querySelectorAll('.dropdown').forEach(dd => {
			dd.classList.remove('show');
			dd.querySelector('.drop-btn')?.setAttribute('aria-expanded', 'false');
		});
		return;
	}

	e.preventDefault();

	const dd = btn.closest('.dropdown');
	const menu = dd?.querySelector('.dropdown-content');
	if (!dd) return;

	const opening = dd.classList.toggle('show');
	btn.setAttribute('aria-expanded', opening ? 'true' : 'false');

	document.querySelectorAll('.dropdown').forEach(d => {
		if (d !== dd) {
			d.classList.remove('show');
			d.querySelector('.drop-btn')?.setAttribute('aria-expanded', 'false');
		}
	});

	if (opening && menu) {
		menu.querySelector('a, button, [tabindex]:not([tabindex="-1"])')?.focus();
	}
});

// Escape closes all
document.addEventListener('keydown', (e) => {
	if (e.key === 'Escape') {
		document.querySelectorAll('.dropdown').forEach(dd => {
			dd.classList.remove('show');
			dd.querySelector('.drop-btn')?.setAttribute('aria-expanded', 'false');
		});
	}
});
//
// // Navbar offset
// (function () {
// 	const navbar = document.querySelector('.navbar');
// 	if (!navbar) return;
//
// 	function updateBodyOffset() {
// 		const h = Math.ceil(navbar.getBoundingClientRect().height);
//
// 		document.documentElement.style.setProperty('--navbar-offset', h + 'px');
// 		document.body.style.paddingTop = h + 'px';
// 	}
//
// 	updateBodyOffset();
// 	window.addEventListener('resize', updateBodyOffset);
// })();