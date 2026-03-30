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
			show = row.style.display === 'none';
		}

		document.querySelectorAll('[id^="warning-"]').forEach(el => {
			if (el.id !== targetId) {
				el.style.display = 'none';
			}
		});

		const isTr = row.tagName.toLowerCase() === 'tr';
		row.style.display = show
			? (isTr ? 'table-row' : 'block')
			: 'none';
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
				el.style.display = 'none';
			});
		}
	});
}
