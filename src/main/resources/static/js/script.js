document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('form').forEach(form => {
    if (form.querySelector('button') && (form.action.includes('delete') || form.action.includes('cancel'))) {
      form.addEventListener('submit', (e) => {
        const confirmed = confirm('Are you sure you want to proceed?');
        if (!confirmed) e.preventDefault();
      });
    }
  });
});