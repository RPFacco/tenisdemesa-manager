(function () {
  'use strict';

  function initTheme() {
    var toggleBtns = document.querySelectorAll('.theme-toggle');
    if (!toggleBtns.length) return;

    toggleBtns.forEach(function (btn) {
      btn.addEventListener('click', function () {
        var html = document.documentElement;
        var current = html.getAttribute('data-theme');
        if (current === 'dark') {
          html.setAttribute('data-theme', 'light');
          localStorage.setItem('theme-preference', 'light');
        } else {
          html.setAttribute('data-theme', 'dark');
          localStorage.setItem('theme-preference', 'dark');
        }
      });
    });
  }

  function initNavbarScroll() {
    var navbar = document.querySelector('.navbar-modern');
    if (!navbar) return;

    var onScroll = function () {
      if (window.scrollY > 20) {
        navbar.classList.add('scrolled');
      } else {
        navbar.classList.remove('scrolled');
      }
    };

    window.addEventListener('scroll', onScroll, { passive: true });
    onScroll();
  }

  function initFloatingLabels() {
    document.querySelectorAll('.form-floating input, .form-floating select, .form-floating textarea').forEach(function (input) {
      if (input.value) {
        input.classList.add('filled');
      }
      input.addEventListener('input', function () {
        if (this.value) {
          this.classList.add('filled');
        } else {
          this.classList.remove('filled');
        }
      });
    });
  }

  window.adicionarSet = function adicionarSet() {
    var container = document.getElementById('sets-container');
    if (!container) return false;
    var count = container.querySelectorAll('.set-row').length;
    var row = document.createElement('div');
    row.className = 'row mb-2 set-row';
    row.innerHTML = '<div class="col-md-4">' +
      '<label class="form-label">Pontos Atleta</label>' +
      '<input type="number" name="sets[' + count + '].pontosAtleta" class="form-control" min="0" required>' +
      '</div>' +
      '<div class="col-md-4">' +
      '<label class="form-label">Pontos Adversário</label>' +
      '<input type="number" name="sets[' + count + '].pontosAdversario" class="form-control" min="0" required>' +
      '</div>' +
      '<div class="col-md-4 d-flex align-items-end mb-3">' +
      '<button type="button" class="btn btn-outline-danger btn-sm remover-set"><i class="bi bi-dash-circle"></i> Remover</button>' +
      '</div>';
    container.appendChild(row);
    return false;
  }

  function initSetsManager() {
    var container = document.getElementById('sets-container');
    if (!container) return;

    container.addEventListener('click', function (e) {
      var btn = e.target.closest('.remover-set');
      if (!btn) return;
      var row = btn.closest('.set-row');
      if (container.querySelectorAll('.set-row').length > 1) {
        row.remove();
        reindexSets(container);
      } else {
        alert('É necessário pelo menos 1 set.');
      }
    });
  }

  function reindexSets(container) {
    var rows = container.querySelectorAll('.set-row');
    rows.forEach(function (row, index) {
      row.querySelectorAll('input').forEach(function (input) {
        input.name = input.name.replace(/sets\[\d+\]/, 'sets[' + index + ']');
      });
    });
  }

  document.addEventListener('DOMContentLoaded', function () {
    initTheme();
    initNavbarScroll();
    initFloatingLabels();
    initSetsManager();
  });
})();
