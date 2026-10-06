# Notes

Search results were wrong, and short searches were delayed on purpose. I fixed those first, then the page controls.

The WHERE clause was missing parentheses. The status filter skipped title matches, and an archived row could still appear when its description matched. I fixed that query in `TaskRepository`, `db/queries/search_tasks.sql`, and the Oracle package.

`TaskController` slept `(10 - query length) * 100` ms before every search. I removed it. An unknown status now returns 400. A bad page or page size is clamped. `%` and `_` are escaped so they are not LIKE wildcards.

On the page, changing the status goes back to page 1. The search box waits 300ms after typing stops, then searches and also returns to page 1. The hook ignores a late response, and it shows the error when a request fails.

I left the `System.out` logging. It is noisy, but it does not change the results, so it was not worth the time.

The biggest remaining risk is paging. Each search still loads every matching row in Java and then slices one page. That is fine for this seed data and will get slow once the table is large.

I used Cursor to read the code, edit the query and the controller, and draft the 300ms search wait in `App.jsx`. I then checked the API and the page myself, including `login` with status DONE, and going from page 2 to the Done filter.
