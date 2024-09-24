import 'package:flutter/material.dart';

/// 由 [TweenAnimationBuilder] 改造的组件，区别在于这个会重复动画。
class RepeatTweenAnimationBuilder<T extends Object?> extends ImplicitlyAnimatedWidget {
  const RepeatTweenAnimationBuilder({
    super.key,
    required this.tween,
    required super.duration,
    super.curve,
    required this.builder,
    super.onEnd,
    this.child,
  });

  final Tween<T> tween;
  final ValueWidgetBuilder<T> builder;
  final Widget? child;

  @override
  ImplicitlyAnimatedWidgetState<ImplicitlyAnimatedWidget> createState() {
    return _TweenAnimationBuilderState<T>();
  }
}

class _TweenAnimationBuilderState<T extends Object?> extends AnimatedWidgetBaseState<RepeatTweenAnimationBuilder<T>> {
  Tween<T>? _currentTween;

  @override
  void initState() {
    _currentTween = widget.tween;
    _currentTween!.begin ??= _currentTween!.end;
    super.initState();
    if (_currentTween!.begin != _currentTween!.end) {
      // 使用 repeat 来重复绘制动画。
      controller.repeat(reverse: true);
    }
  }

  @override
  void forEachTween(TweenVisitor<dynamic> visitor) {
    assert(
      widget.tween.end != null,
      'Tween provided to TweenAnimationBuilder must have non-null Tween.end value.',
    );
    _currentTween = visitor(_currentTween, widget.tween.end, (dynamic value) {
      assert(false);
      throw StateError('Constructor will never be called because null is never provided as current tween.');
    }) as Tween<T>?;
  }

  @override
  Widget build(BuildContext context) {
    return widget.builder(context, _currentTween!.evaluate(animation), widget.child);
  }
}