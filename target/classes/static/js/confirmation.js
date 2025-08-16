function toggleWarning(id, show) {
	const row = document.getElementById("warning-" + id);
	if (row) row.style.display = show ? "table-row" : "none";
}