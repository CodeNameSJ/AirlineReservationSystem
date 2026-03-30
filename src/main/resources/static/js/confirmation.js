if (!window.__confirmationInit) {
	window.__confirmationInit = true;

	window.toggleWarning = function toggleWarning(id, type, show = null) {
		let prefix = 'warning-';
		if (type === 'cancel') prefix = 'warning-cancel-';
		else if (type === 'delete') prefix = 'warning-delete-';
		else if (type === 'user') prefix = 'warning-user-';

		const targetId = prefix + id;
		const row = document.getElementById(targetId);
		if (!row) return;

		if (show === null) {
			show = !row.classList.contains('is-open');
		}

		document.querySelectorAll('[id^="warning-"]').forEach(el => {
			if (el.id !== targetId) {
				el.classList.remove('is-open');
			}
		});

		row.classList.toggle('is-open', show);

		document.querySelectorAll(`.warn-toggle[data-id="${id}"][data-type="${type}"]`).forEach(btn => {
			btn.setAttribute('aria-expanded', String(show));
		});
	};

	document.addEventListener('click', (e) => {
		const btn = e.target.closest('.warn-toggle');
		if (!btn) return;

		const id = btn.dataset.id;
		const type = btn.dataset.type;

		window.toggleWarning(id, type);
	});

	document.addEventListener('click', (e) => {
		if (!e.target.closest('.warn-toggle') &&
			!e.target.closest('[id^="warning-"]')) {

			document.querySelectorAll('[id^="warning-"]').forEach(el => {
				el.classList.remove('is-open');
			});
			document.querySelectorAll('.warn-toggle[aria-expanded="true"]').forEach(btn => {
				btn.setAttribute('aria-expanded', 'false');
			});
		}
	});
}
