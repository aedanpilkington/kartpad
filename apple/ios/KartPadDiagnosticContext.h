#pragma once
#import <Foundation/Foundation.h>

// Same schema/field semantics as Android's KartPadReportContext. No network or raw file data.
static inline NSString *KartPadDiagnosticContext(NSString *versionPath, NSString *supported,
                                                NSString *profile, double resolution, NSInteger aspect) {
  NSString *installed = nil;
  @try {
    NSFileHandle *file = [NSFileHandle fileHandleForReadingAtPath:versionPath];
    if (file != nil) {
      NSData *data = [file readDataOfLength:129];
      [file closeFile];
      if (data.length <= 128) {
        NSString *text = [[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]
            stringByTrimmingCharactersInSet:NSCharacterSet.whitespaceAndNewlineCharacterSet];
        NSRegularExpression *pattern = [NSRegularExpression regularExpressionWithPattern:
            @"^[0-9]+(\\.[0-9]+){1,3}$" options:0 error:nil];
        NSTextCheckingResult *match = [pattern firstMatchInString:text ?: @"" options:0
            range:NSMakeRange(0, text.length)];
        if (text.length <= 64 && match != nil && NSEqualRanges(match.range, NSMakeRange(0, text.length))) installed = text;
      }
    }
  } @catch (NSException *exception) { (void)exception; }
  NSString *state = ![NSFileManager.defaultManager fileExistsAtPath:versionPath] ? @"not_installed"
      : installed == nil ? @"unreadable_or_invalid"
      : [installed isEqualToString:supported] ? @"version_match_only" : @"version_mismatch";
  NSBundle *bundle = NSBundle.mainBundle;
  NSDictionary *context = @{
    @"schema": @1, @"platform": @"ios",
    @"app_version": [bundle objectForInfoDictionaryKey:@"CFBundleShortVersionString"] ?: @"unknown",
    @"app_build": [bundle objectForInfoDictionaryKey:@"CFBundleVersion"] ?: @"unknown",
    @"runtime_profile": profile ?: @"unknown",
    @"captured_unix_ms": @((long long)(NSDate.date.timeIntervalSince1970 * 1000.0)),
    @"monotonic_ms": @((long long)(NSProcessInfo.processInfo.systemUptime * 1000.0)),
    @"monotonic_clock": @"apple_system_uptime",
    @"retro_supported_version": supported,
    @"retro_installed_version": installed ?: NSNull.null,
    @"retro_version_state": state,
    @"retro_code_validation": @"not_rechecked_for_report",
    @"resolution_scale": @(resolution), @"aspect_mode": @(aspect),
    @"renderer_validation": NSNull.null,
  };
  NSData *json = [NSJSONSerialization dataWithJSONObject:context options:NSJSONWritingSortedKeys error:nil];
  return json ? [[NSString alloc] initWithData:json encoding:NSUTF8StringEncoding] : @"{\"schema\":1,\"error\":\"context_unavailable\"}";
}
