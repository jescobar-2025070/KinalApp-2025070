/**
 * KinalApp — JS principal (Material Design dark theme)
 */
document.addEventListener('DOMContentLoaded', function () {

    // ── 1. SIDEBAR TOGGLE ────────────────────────────────────
    const toggleBtn   = document.getElementById('sidebarToggle');
    const sidebar     = document.getElementById('kSidebar');
    const pageContent = document.getElementById('page-content');

    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener('click', function () {
            if (window.innerWidth <= 768) {
                sidebar.classList.toggle('mobile-open');
            } else {
                sidebar.classList.toggle('collapsed');
                if (pageContent) pageContent.classList.toggle('expanded');
            }
        });

        // Cerrar sidebar al tocar fuera (móvil)
        document.addEventListener('click', function (e) {
            if (window.innerWidth <= 768
                && sidebar.classList.contains('mobile-open')
                && !sidebar.contains(e.target)
                && !toggleBtn.contains(e.target)) {
                sidebar.classList.remove('mobile-open');
            }
        });
    }

    // ── 2. BUSCADOR EN TABLA ─────────────────────────────────
    const buscador = document.getElementById('buscador');
    if (buscador) {
        const tbody = document.querySelector('.k-table tbody');
        buscador.addEventListener('input', function () {
            const q = this.value.toLowerCase().trim();
            if (!tbody) return;
            Array.from(tbody.rows).forEach(function (row) {
                if (row.cells.length <= 1) return;
                row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
            });
        });
    }

    // ── 3. AUTO-CERRAR ALERTAS (5s) ─────────────────────────
    document.querySelectorAll('.k-alert[data-auto-close]').forEach(function (el) {
        setTimeout(function () {
            el.style.transition = 'opacity 0.4s';
            el.style.opacity = '0';
            setTimeout(function () { el.remove(); }, 400);
        }, 5000);
    });

    // ── 4. CERRAR ALERTAS AL CLICK ──────────────────────────
    document.querySelectorAll('.k-alert-close').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const alert = btn.closest('.k-alert');
            if (alert) {
                alert.style.transition = 'opacity 0.3s';
                alert.style.opacity = '0';
                setTimeout(function () { alert.remove(); }, 300);
            }
        });
    });

    // ── 5. MODAL GLOBAL ──────────────────────────────────────
    window.kModal = {
        open: function (id) {
            const el = document.getElementById(id);
            if (el) el.classList.add('open');
        },
        close: function (id) {
            const el = document.getElementById(id);
            if (el) el.classList.remove('open');
        }
    };

    // Cerrar modal al hacer clic en el overlay
    document.querySelectorAll('.k-modal-overlay').forEach(function (overlay) {
        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) overlay.classList.remove('open');
        });
    });

    // Botones con data-close-modal
    document.querySelectorAll('[data-close-modal]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const id = btn.getAttribute('data-close-modal');
            kModal.close(id);
        });
    });

    // ── 6. VALIDACIÓN FORMULARIOS ────────────────────────────
    document.querySelectorAll('form.k-needs-validation').forEach(function (form) {
        form.addEventListener('submit', function (e) {
            if (!form.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

});
