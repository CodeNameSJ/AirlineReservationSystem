// function toggleWarning(id, typeOrShow, maybeShow) {
// 	// normalize arguments
// 	let type;
// 	let show;
//
// 	if (typeof typeOrShow === 'string') {
// 		type = typeOrShow;
// 		show = (typeof maybeShow === 'undefined') ? true : Boolean(maybeShow);
// 	} else {
// 		// legacy signature: second arg is boolean (show/hide)
// 		type = null;
// 		show = Boolean(typeOrShow);
// 	}
//
// 	// determine prefix
// 	let prefix;
// 	if (type === 'cancel') prefix = 'warning-cancel-';
// 	else if (type === 'delete') prefix = 'warning-delete-';
// 	else if (type === 'user') prefix = 'warning-user-';
// 	else prefix = 'warning-';
//
// 	// hide all other warning rows of the same family before showing (keeps UI tidy)
// 	const allSelectors = [
// 		'[id^="warning-cancel-"]',
// 		'[id^="warning-delete-"]',
// 		'[id^="warning-user-"]',
// 		'[id^="warning-"]'
// 	].join(',');
// 	document.querySelectorAll(allSelectors).forEach(el => {
// 		// if current element is the one we want to toggle, skip for now
// 		if (el.id === prefix + id) return;
// 		// hide everything else
// 		el.style.display = 'none';
// 	});
//
// 	const el = document.getElementById(prefix + id);
// 	if (!el) return;
//
// 	// table rows must use 'table-row' to display correctly
// 	const isTr = el.tagName && el.tagName.toLowerCase() === 'tr';
// 	if (show) {
// 		el.style.display = isTr ? 'table-row' : 'block';
// 	} else {
// 		el.style.display = 'none';
// 	}
// }

function toggleWarning(id, typeOrShow, show = null) {
	// Determine parameters: if typeOrShow is boolean, then no type given
	let type = typeOrShow;
	if (typeof typeOrShow === 'boolean') {
		show = typeOrShow;
		type = null;
	}
	let selector;
	if (type === 'cancel') {
		selector = `#warning-cancel-${id}`;
	} else if (type === 'delete') {
		selector = `#warning-delete-${id}`;
	} else if (type === 'user') {
		selector = `#warning-user-${id}`;
	} else {
		selector = `#warning-${id}`;
	}
	const row = document.querySelector(selector);
	if (row) {
		row.style.display = show ? '' : 'none';
	}
}