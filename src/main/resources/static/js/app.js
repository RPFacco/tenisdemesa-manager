(function () {
  'use strict';

  var prefersReducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

  function initTheme() {
    var savedTheme = localStorage.getItem('theme-preference');
    if (savedTheme === 'dark') {
      document.documentElement.setAttribute('data-theme', 'dark');
    } else if (savedTheme === 'light') {
      document.documentElement.setAttribute('data-theme', 'light');
    }

    var toggleBtn = document.getElementById('theme-toggle');
    if (toggleBtn) {
      toggleBtn.addEventListener('click', function () {
        var html = document.documentElement;
        var current = html.getAttribute('data-theme');
        if (current === 'dark') {
          html.setAttribute('data-theme', 'light');
          localStorage.setItem('theme-preference', 'light');
          toggleBtn.innerHTML = '<i class="bi bi-moon-stars"></i>';
        } else {
          html.setAttribute('data-theme', 'dark');
          localStorage.setItem('theme-preference', 'dark');
          toggleBtn.innerHTML = '<i class="bi bi-sun"></i>';
        }
      });

      var currentTheme = document.documentElement.getAttribute('data-theme');
      toggleBtn.innerHTML = currentTheme === 'dark'
        ? '<i class="bi bi-sun"></i>'
        : '<i class="bi bi-moon-stars"></i>';
    }
  }

  function initScrollAnimations() {
    if (prefersReducedMotion) return;

    var els = document.querySelectorAll('.animate-on-scroll');
    if (!els.length) return;

    var observer = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          observer.unobserve(entry.target);
        }
      });
    }, {
      threshold: 0.1,
      rootMargin: '0px 0px -40px 0px'
    });

    els.forEach(function (el) {
      if (el.getBoundingClientRect().top < window.innerHeight) {
        el.classList.add('visible');
      } else {
        observer.observe(el);
      }
    });
  }

  function initAnimatedCounters() {
    if (prefersReducedMotion) return;

    var counters = document.querySelectorAll('.animate-counter');
    if (!counters.length) return;

    counters.forEach(function (el) {
      var target = parseFloat(el.textContent.trim()) || 0;
      var isPercentage = el.textContent.trim().endsWith('%');
      var prefix = el.dataset.prefix || '';
      var suffix = el.dataset.suffix || (isPercentage ? '%' : '');
      var duration = parseInt(el.dataset.duration) || 1500;
      var startTime = null;

      el.textContent = prefix + '0' + suffix;

      var observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
          if (entry.isIntersecting) {
            observer.unobserve(el);
            animate(el, target, prefix, suffix, duration);
          }
        });
      }, { threshold: 0.3 });

      observer.observe(el);

      function animate(el, target, prefix, suffix, duration) {
        function step(timestamp) {
          if (!startTime) startTime = timestamp;
          var progress = Math.min((timestamp - startTime) / duration, 1);
          var eased = 1 - Math.pow(1 - progress, 3);
          var current = Math.round(eased * target);

          if (isPercentage) {
            el.textContent = prefix + current + suffix;
          } else {
            el.textContent = prefix + current + suffix;
          }

          if (progress < 1) {
            requestAnimationFrame(step);
          }
        }
        requestAnimationFrame(step);
      }
    });
  }

  function initStaggeredAnimations() {
    if (prefersReducedMotion) return;

    var containers = document.querySelectorAll('.stagger-children');
    containers.forEach(function (container) {
      var children = container.children;
      if (!children.length) return;

      var observer = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
          if (entry.isIntersecting) {
            observer.unobserve(container);
            Array.from(children).forEach(function (child, index) {
              child.style.transitionDelay = (index * 100) + 'ms';
            });
            container.classList.add('loaded');
          }
        });
      }, { threshold: 0.1 });

      observer.observe(container);
    });
  }

  function initNavbarScroll() {
    var navbar = document.querySelector('.navbar-modern');
    if (!navbar) return;

    var onScroll = function () {
      if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
      } else {
        navbar.classList.remove('scrolled');
      }
    };

    window.addEventListener('scroll', onScroll, { passive: true });
    onScroll();
  }

  function initCard3D() {
    var cards = document.querySelectorAll('.card-3d');
    if (!cards.length || prefersReducedMotion) return;

    cards.forEach(function (card) {
      card.addEventListener('mousemove', function (e) {
        var rect = card.getBoundingClientRect();
        var x = e.clientX - rect.left;
        var y = e.clientY - rect.top;
        var centerX = rect.width / 2;
        var centerY = rect.height / 2;
        var rotateX = (y - centerY) / centerY * -8;
        var rotateY = (x - centerX) / centerX * 8;
        card.style.transform = 'perspective(1000px) rotateX(' + rotateX + 'deg) rotateY(' + rotateY + 'deg) scale3d(1.02, 1.02, 1.02)';
      });

      card.addEventListener('mouseleave', function () {
        card.style.transform = 'perspective(1000px) rotateX(0) rotateY(0) scale3d(1, 1, 1)';
      });
    });
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

  document.addEventListener('DOMContentLoaded', function () {
    initTheme();
    initScrollAnimations();
    initAnimatedCounters();
    initStaggeredAnimations();
    initNavbarScroll();
    initCard3D();
    initFloatingLabels();
  });
})();
