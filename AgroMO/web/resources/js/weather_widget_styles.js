!function (e) {
    function t(e) {
        delete installedChunks[e]
    }

    function r(e) {
        var t = document.getElementsByTagName("head")[0],
                r = document.createElement("script");
        r.type = "text/javascript", r.charset = "utf-8", r.src = f.p + "" + e + "." + g + ".hot-update.js", t.appendChild(r)
    }

    function n(e) {
        return e = e || 1e4, new Promise(function (t, r) {
            if ("undefined" == typeof XMLHttpRequest)
                return r(Error("No browser support"));
            try {
                var n = new XMLHttpRequest,
                        o = f.p + "" + g + ".hot-update.json";
                n.open("GET", o, !0), n.timeout = e, n.send(null)
            } catch (e) {
                return r(e)
            }
            n.onreadystatechange = function () {
                if (4 === n.readyState)
                    if (0 === n.status)
                        r(Error("Manifest request to " + o + " timed out."));
                    else if (404 === n.status)
                        t();
                    else if (200 !== n.status && 304 !== n.status)
                        r(Error("Manifest request to " + o + " failed."));
                    else {
                        try {
                            var e = JSON.parse(n.responseText)
                        } catch (e) {
                            return void r(e)
                        }
                        t(e)
                    }
            }
        })
    }

    function o(e) {
        var t = M[e];
        if (!t)
            return f;
        var r = function (r) {
            return t.hot.active ? (M[r] ? M[r].parents.indexOf(e) < 0 && M[r].parents.push(e) : (k = [e], m = r), t.children.indexOf(r) < 0 && t.children.push(r)) : k = [], f(r)
        };
        for (var n in f)
            Object.prototype.hasOwnProperty.call(f, n) && "e" !== n && Object.defineProperty(r, n, function (e) {
                return {
                    configurable: !0,
                    enumerable: !0,
                    get: function () {
                        return f[e]
                    },
                    set: function (t) {
                        f[e] = t
                    }
                }
            }(n));
        return r.e = function (e) {
            function t() {
                P--, "prepare" === j && (H[e] || s(e), 0 === P && 0 === x && p())
            }
            return "ready" === j && i("prepare"), P++, f.e(e).then(t, function (e) {
                throw t(), e
            })
        }, r
    }

    function a(e) {
        var t = {
            _acceptedDependencies: {},
            _declinedDependencies: {},
            _selfAccepted: !1,
            _selfDeclined: !1,
            _disposeHandlers: [],
            _main: m !== e,
            active: !0,
            accept: function (e, r) {
                if (void 0 === e)
                    t._selfAccepted = !0;
                else if ("function" == typeof e)
                    t._selfAccepted = e;
                else if ("object" == typeof e)
                    for (var n = 0; n < e.length; n++)
                        t._acceptedDependencies[e[n]] = r || function () {};
                else
                    t._acceptedDependencies[e] = r || function () {}
            },
            decline: function (e) {
                if (void 0 === e)
                    t._selfDeclined = !0;
                else if ("object" == typeof e)
                    for (var r = 0; r < e.length; r++)
                        t._declinedDependencies[e[r]] = !0;
                else
                    t._declinedDependencies[e] = !0
            },
            dispose: function (e) {
                t._disposeHandlers.push(e)
            },
            addDisposeHandler: function (e) {
                t._disposeHandlers.push(e)
            },
            removeDisposeHandler: function (e) {
                var r = t._disposeHandlers.indexOf(e);
                r >= 0 && t._disposeHandlers.splice(r, 1)
            },
            check: l,
            apply: u,
            status: function (e) {
                if (!e)
                    return j;
                E.push(e)
            },
            addStatusHandler: function (e) {
                E.push(e)
            },
            removeStatusHandler: function (e) {
                var t = E.indexOf(e);
                t >= 0 && E.splice(t, 1)
            },
            data: O[e]
        };
        return m = void 0, t
    }

    function i(e) {
        j = e;
        for (var t = 0; t < E.length; t++)
            E[t].call(null, e)
    }

    function c(e) {
        return +e + "" === e ? +e : e
    }

    function l(e) {
        if ("idle" !== j)
            throw Error("check() is only allowed in idle status");
        return b = e, i("check"), n(w).then(function (e) {
            if (!e)
                return i("idle"), null;
            I = {}, H = {}, A = e.c, _ = e.h, i("prepare");
            var t = new Promise(function (e, t) {
                y = {
                    resolve: e,
                    reject: t
                }
            });
            v = {};
            return s(5), "prepare" === j && 0 === P && 0 === x && p(), t
        })
    }

    function d(e, t) {
        if (A[e] && I[e]) {
            I[e] = !1;
            for (var r in t)
                Object.prototype.hasOwnProperty.call(t, r) && (v[r] = t[r]);
            0 == --x && 0 === P && p()
        }
    }

    function s(e) {
        A[e] ? (I[e] = !0, x++, r(e)) : H[e] = !0
    }

    function p() {
        i("ready");
        var e = y;
        if (y = null, e)
            if (b)
                Promise.resolve().then(function () {
                    return u(b)
                }).then(function (t) {
                    e.resolve(t)
                }, function (t) {
                    e.reject(t)
                });
            else {
                var t = [];
                for (var r in v)
                    Object.prototype.hasOwnProperty.call(v, r) && t.push(c(r));
                e.resolve(t)
            }
    }

    function u(r) {
        function n(e, t) {
            for (var r = 0; r < t.length; r++) {
                var n = t[r];
                e.indexOf(n) < 0 && e.push(n)
            }
        }
        if ("ready" !== j)
            throw Error("apply() is only allowed in ready status");
        r = r || {};
        var o, a, l, d, s, p = {},
                u = [],
                h = {},
                m = function () {};
        for (var y in v)
            if (Object.prototype.hasOwnProperty.call(v, y)) {
                s = c(y);
                var b;
                b = v[y] ? function (e) {
                    for (var t = [e], r = {}, o = t.slice().map(function (e) {
                        return {
                            chain: [e],
                            id: e
                        }
                    }); o.length > 0; ) {
                        var a = o.pop(),
                                i = a.id,
                                c = a.chain;
                        if ((d = M[i]) && !d.hot._selfAccepted) {
                            if (d.hot._selfDeclined)
                                return {
                                    type: "self-declined",
                                    chain: c,
                                    moduleId: i
                                };
                            if (d.hot._main)
                                return {
                                    type: "unaccepted",
                                    chain: c,
                                    moduleId: i
                                };
                            for (var l = 0; l < d.parents.length; l++) {
                                var s = d.parents[l],
                                        p = M[s];
                                if (p) {
                                    if (p.hot._declinedDependencies[i])
                                        return {
                                            type: "declined",
                                            chain: c.concat([s]),
                                            moduleId: i,
                                            parentId: s
                                        };
                                    t.indexOf(s) >= 0 || (p.hot._acceptedDependencies[i] ? (r[s] || (r[s] = []), n(r[s], [i])) : (delete r[s], t.push(s), o.push({
                                        chain: c.concat([s]),
                                        id: s
                                    })))
                                }
                            }
                        }
                    }
                    return {
                        type: "accepted",
                        moduleId: e,
                        outdatedModules: t,
                        outdatedDependencies: r
                    }
                }(s) : {
                    type: "disposed",
                    moduleId: y
                };
                var w = !1,
                        D = !1,
                        E = !1,
                        x = "";
                switch (b.chain && (x = "\nUpdate propagation: " + b.chain.join(" -> ")), b.type) {
                    case "self-declined":
                        r.onDeclined && r.onDeclined(b), r.ignoreDeclined || (w = Error("Aborted because of self decline: " + b.moduleId + x));
                        break;
                    case "declined":
                        r.onDeclined && r.onDeclined(b), r.ignoreDeclined || (w = Error("Aborted because of declined dependency: " + b.moduleId + " in " + b.parentId + x));
                        break;
                    case "unaccepted":
                        r.onUnaccepted && r.onUnaccepted(b), r.ignoreUnaccepted || (w = Error("Aborted because " + s + " is not accepted" + x));
                        break;
                    case "accepted":
                        r.onAccepted && r.onAccepted(b), D = !0;
                        break;
                    case "disposed":
                        r.onDisposed && r.onDisposed(b), E = !0;
                        break;
                    default:
                        throw Error("Unexception type " + b.type)
                }
                if (w)
                    return i("abort"), Promise.reject(w);
                if (D) {
                    h[s] = v[s], n(u, b.outdatedModules);
                    for (s in b.outdatedDependencies)
                        Object.prototype.hasOwnProperty.call(b.outdatedDependencies, s) && (p[s] || (p[s] = []), n(p[s], b.outdatedDependencies[s]))
                }
                E && (n(u, [b.moduleId]), h[s] = m)
            }
        var P = [];
        for (a = 0; a < u.length; a++)
            s = u[a], M[s] && M[s].hot._selfAccepted && P.push({
                module: s,
                errorHandler: M[s].hot._selfAccepted
            });
        i("dispose"), Object.keys(A).forEach(function (e) {
            !1 === A[e] && t(e)
        });
        for (var H, I = u.slice(); I.length > 0; )
            if (s = I.pop(), d = M[s]) {
                var U = {},
                        q = d.hot._disposeHandlers;
                for (l = 0; l < q.length; l++)
                    (o = q[l])(U);
                for (O[s] = U, d.hot.active = !1, delete M[s], delete p[s], l = 0; l < d.children.length; l++) {
                    var C = M[d.children[l]];
                    C && ((H = C.parents.indexOf(s)) >= 0 && C.parents.splice(H, 1))
                }
            }
        var S, B;
        for (s in p)
            if (Object.prototype.hasOwnProperty.call(p, s) && (d = M[s]))
                for (B = p[s], l = 0; l < B.length; l++)
                    S = B[l], (H = d.children.indexOf(S)) >= 0 && d.children.splice(H, 1);
        i("apply"), g = _;
        for (s in h)
            Object.prototype.hasOwnProperty.call(h, s) && (e[s] = h[s]);
        var N = null;
        for (s in p)
            if (Object.prototype.hasOwnProperty.call(p, s) && (d = M[s])) {
                B = p[s];
                var T = [];
                for (a = 0; a < B.length; a++)
                    if (S = B[a], o = d.hot._acceptedDependencies[S]) {
                        if (T.indexOf(o) >= 0)
                            continue;
                        T.push(o)
                    }
                for (a = 0; a < T.length; a++) {
                    o = T[a];
                    try {
                        o(B)
                    } catch (e) {
                        r.onErrored && r.onErrored({
                            type: "accept-errored",
                            moduleId: s,
                            dependencyId: B[a],
                            error: e
                        }), r.ignoreErrored || N || (N = e)
                    }
                }
            }
        for (a = 0; a < P.length; a++) {
            var L = P[a];
            s = L.module, k = [s];
            try {
                f(s)
            } catch (e) {
                if ("function" == typeof L.errorHandler)
                    try {
                        L.errorHandler(e)
                    } catch (t) {
                        r.onErrored && r.onErrored({
                            type: "self-accept-error-handler-errored",
                            moduleId: s,
                            error: t,
                            orginalError: e,
                            originalError: e
                        }), r.ignoreErrored || N || (N = t), N || (N = e)
                    }
                else
                    r.onErrored && r.onErrored({
                        type: "self-accept-errored",
                        moduleId: s,
                        error: e
                    }), r.ignoreErrored || N || (N = e)
            }
        }
        return N ? (i("fail"), Promise.reject(N)) : (i("idle"), new Promise(function (e) {
            e(u)
        }))
    }

    function f(t) {
        if (M[t])
            return M[t].exports;
        var r = M[t] = {
            i: t,
            l: !1,
            exports: {},
            hot: a(t),
            parents: (D = k, k = [], D),
            children: []
        };
        return e[t].call(r.exports, r, r.exports, o(t)), r.l = !0, r.exports
    }
    var h = window.webpackHotUpdate;
    window.webpackHotUpdate = function (e, t) {
        d(e, t), h && h(e, t)
    };
    var m, y, v, _, b = !0,
            g = "c31872127471c475ade0",
            w = 1e4,
            O = {},
            k = [],
            D = [],
            E = [],
            j = "idle",
            x = 0,
            P = 0,
            H = {},
            I = {},
            A = {},
            M = {};
    f.m = e, f.c = M, f.d = function (e, t, r) {
        f.o(e, t) || Object.defineProperty(e, t, {
            configurable: !1,
            enumerable: !0,
            get: r
        })
    }, f.n = function (e) {
        var t = e && e.__esModule ? function () {
            return e.default
        } : function () {
            return e
        };
        return f.d(t, "a", t), t
    }, f.o = function (e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }, f.p = "/", f.h = function () {
        return g
    }, o(1068)(f.s = 1068)
}({
    1068: function (e, t) {
        e.exports = {
            olPopupCloseBox: "olPopupCloseBox",
            olPopup: "olPopup",
            weather_title: "weather_title",
            weather_block: "weather_block",
            small_temp_block: "small_temp_block",
            big_temp: "big_temp",
            small_temp: "small_temp",
            small_temp_grey: "small_temp_grey",
            small_val: "small_val",
            small_val_grey: "small_val_grey",
            small_val2: "small_val2",
            small_val2_grey: "small_val2_grey",
            weather_image: "weather_image",
            station_image: "station_image",
            cur_weather_block: "cur_weather_block",
            temp_block: "temp_block",
            "vote-plus": "vote-plus",
            "vote-minus": "vote-minus",
            switch : "switch",
            "slide-button": "slide-button",
            toggle: "toggle",
            "switch-three": "switch-three",
            "switch-four": "switch-four",
            "switch-five": "switch-five",
            bugfix: "bugfix",
            candy: "candy",
            blue: "blue",
            yellow: "yellow",
            android: "android",
            ios: "ios",
            fade: "fade",
            in: "in",
            close: "close",
            "modal-open": "modal-open",
            modal: "modal",
            "modal-dialog": "modal-dialog",
            "modal-content": "modal-content",
            "modal-backdrop": "modal-backdrop",
            "modal-header": "modal-header",
            "modal-title": "modal-title",
            "modal-body": "modal-body",
            "modal-footer": "modal-footer",
            btn: "btn",
            "btn-group": "btn-group",
            "btn-block": "btn-block",
            "modal-sm": "modal-sm",
            "modal-lg": "modal-lg",
            animated: "animated",
            shake: "shake"
        }
    }
});